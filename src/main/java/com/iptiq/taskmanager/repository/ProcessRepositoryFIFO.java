package com.iptiq.taskmanager.repository;

import com.iptiq.taskmanager.domain.Process;
import com.iptiq.taskmanager.domain.Process.Priority;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class ProcessRepositoryFIFO implements ProcessRepository {

    private static final int FIRST_PROCESS = 0;
    private final ProcessRepository processRepository;
    private final ExecutorService executorService;
    private final int capacity;

    public ProcessRepositoryFIFO(int capacity) {
        this.capacity = capacity;
        this.processRepository = new ProcessRepositoryDefault(this.capacity);
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void addProcess(Process process) {
        Callable<Boolean> addProcessAsync = addProcessAsync(process);
        submitAsyncTask(addProcessAsync);
    }

    @Override
    public List<Process> listAllProcesses() {
        return processRepository.listAllProcesses();
    }

    @Override
    public void killProcess(Process process) {
        Callable<Boolean> asyncTask = killProcessAsync(process);
        submitAsyncTask(asyncTask);
    }

    @Override
    public void killAllProcessesByGroup(Priority priority) {
        Callable<Boolean> asyncTask = killAllProcessesByGroupAsync(priority);
        submitAsyncTask(asyncTask);
    }

    @Override
    public void killAllProcesses() {
        Callable<Boolean> asyncTask = killAllProcessesAsync();
        submitAsyncTask(asyncTask);
    }

    private Callable<Boolean> killAllProcessesAsync() {
        return () -> {
            processRepository.killAllProcesses();
            return true;
        };
    }

    private Callable<Boolean> killAllProcessesByGroupAsync(Priority priority) {
        return () -> {
            processRepository.killAllProcessesByGroup(priority);
            return true;
        };
    }

    private void submitAsyncTask(Callable<Boolean> asyncTask) {
        try {
            executorService.invokeAll(Collections.singletonList(asyncTask));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Callable<Boolean> killProcessAsync(Process process) {
        return () -> {
            processRepository.killProcess(process);
            return true;
        };
    }

    private Callable<Boolean> addProcessAsync(Process process) {
        return () -> {
            List<Process> processes = listAllProcesses();
            if (processes.size() == capacity) {
                processRepository.killProcess(processes.get(FIRST_PROCESS));
            }
            processRepository.addProcess(process);
            return true;
        };
    }

}
