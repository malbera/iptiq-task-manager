package com.iptiq.taskmanager.repository;

import com.iptiq.taskmanager.domain.AssignedProcess;
import com.iptiq.taskmanager.domain.Process;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessRepositoryAsyncCalls implements ProcessRepository {

    private final ProcessRepository processRepository;
    private final ExecutorService executorService;

    public ProcessRepositoryAsyncCalls(ProcessRepository processRepository) {
        this.processRepository = processRepository;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void addProcess(Process process) {
        submitAsyncTask(() -> {
            processRepository.addProcess(process);
            return true;
        });
    }

    @Override
    public List<AssignedProcess> listAllAssignedProcesses() {
        return processRepository.listAllAssignedProcesses();
    }

    @Override
    public void killProcess(Process process) {
        submitAsyncTask(() -> {
            processRepository.killProcess(process);
            return true;
        });
    }

    @Override
    public void killAllProcessesByGroup(Process.Priority priority) {
        submitAsyncTask(() -> {
            processRepository.killAllProcessesByGroup(priority);
            return true;
        });
    }

    @Override
    public void killAllProcesses() {
        submitAsyncTask(() -> {
            processRepository.killAllProcesses();
            return true;
        });
    }

    private void submitAsyncTask(Callable<Boolean> asyncTask) {
        try {
            executorService.invokeAll(Collections.singletonList(asyncTask));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
