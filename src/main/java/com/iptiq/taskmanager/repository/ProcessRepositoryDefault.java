package com.iptiq.taskmanager.repository;

import com.iptiq.taskmanager.domain.AssignedProcess;
import com.iptiq.taskmanager.domain.Process;
import com.iptiq.taskmanager.domain.Process.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProcessRepositoryDefault implements ProcessRepository {

    private final BlockingQueue<AssignedProcess> processes;

    public ProcessRepositoryDefault(int capacity) {
        this.processes = new ArrayBlockingQueue<>(capacity);
    }

    public ProcessRepositoryDefault(BlockingQueue<AssignedProcess> processes) {
        this.processes = processes;
    }

    @Override
    public void addProcess(Process process) {
        processes.offer(new AssignedProcess(process));
    }

    @Override
    public List<AssignedProcess> listAllAssignedProcesses() {
        return new ArrayList<>(processes);
    }

    @Override
    public void killProcess(Process process) {
        processes.removeIf(ap -> ap.getProcess().equals(process));
    }

    @Override
    public void killAllProcessesByGroup(Priority priority) {
        processes.removeIf(ap -> ap.getProcess().getPriority() == priority);
    }

    @Override
    public void killAllProcesses() {
        processes.clear();
    }
}
