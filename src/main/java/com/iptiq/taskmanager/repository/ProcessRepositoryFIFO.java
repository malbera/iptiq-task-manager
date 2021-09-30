package com.iptiq.taskmanager.repository;

import com.iptiq.taskmanager.domain.AssignedProcess;
import com.iptiq.taskmanager.domain.Process;
import com.iptiq.taskmanager.domain.Process.Priority;

import java.util.List;

public class ProcessRepositoryFIFO implements ProcessRepository {

    private static final int OLDEST_ASSIGNED = 0;
    private final ProcessRepository processRepository;
    private final int capacity;

    public ProcessRepositoryFIFO(int capacity) {
        this.capacity = capacity;
        this.processRepository = new ProcessRepositoryDefault(this.capacity);
    }

    @Override
    public void addProcess(Process process) {
        List<AssignedProcess> processes = listAllAssignedProcesses();
        if (processes.size() == capacity) {
            AssignedProcess oldestAssignedProcess = processes.get(OLDEST_ASSIGNED);
            processRepository.killProcess(oldestAssignedProcess.getProcess());
        }
        processRepository.addProcess(process);
    }

    @Override
    public List<AssignedProcess> listAllAssignedProcesses() {
        return processRepository.listAllAssignedProcesses();
    }

    @Override
    public void killProcess(Process process) {
        processRepository.killProcess(process);
    }

    @Override
    public void killAllProcessesByGroup(Priority priority) {
        processRepository.killAllProcessesByGroup(priority);
    }

    @Override
    public void killAllProcesses() {
        processRepository.killAllProcesses();
    }

}
