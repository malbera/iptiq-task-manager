package com.iptiq.taskmanager.repository;

import com.iptiq.taskmanager.domain.Process;
import com.iptiq.taskmanager.domain.Process.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProcessRepositoryDefault implements ProcessRepository {

    private final BlockingQueue<Process> processes;

    public ProcessRepositoryDefault(int capacity) {
        this.processes = new ArrayBlockingQueue<>(capacity);
    }

    @Override
    public void addProcess(Process process) {
        processes.offer(process);
    }

    @Override
    public List<Process> listAllProcesses() {
        return new ArrayList<>(processes);
    }

    @Override
    public void killProcess(Process process) {
        processes.remove(process);
    }

    @Override
    public void killAllProcessesByGroup(Priority priority) {
        processes.removeIf(p -> p.getPriority() == priority);
    }

    @Override
    public void killAllProcesses() {
        processes.clear();
    }
}
