package com.iptiq.taskmanager.repository;

import com.iptiq.taskmanager.domain.AssignedProcess;
import com.iptiq.taskmanager.domain.Process;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProcessRepositoryPriorityBased implements ProcessRepository {

    private final ProcessRepository processRepository;
    private final int capacity;

    public ProcessRepositoryPriorityBased(int capacity) {
        this.capacity = capacity;
        this.processRepository = new ProcessRepositoryDefault(capacity);
    }

    @Override
    public void addProcess(Process process) {
        List<AssignedProcess> processes = listAllAssignedProcesses()
                .stream()
                .sorted(assignedProcessComparator())
                .collect(Collectors.toList());
        if (processes.size() == capacity) {
            AssignedProcess lowestPriority = processes.get(capacity - 1);
            processRepository.killProcess(lowestPriority.getProcess());
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
    public void killAllProcessesByGroup(Process.Priority priority) {
        processRepository.killAllProcessesByGroup(priority);
    }

    @Override
    public void killAllProcesses() {
        processRepository.killAllProcesses();
    }

    public static Comparator<AssignedProcess> assignedProcessComparator() {
        return Comparator.comparing((AssignedProcess ap) -> ap.getProcess().getPriority().getPriorityValue())
                .reversed()
                .thenComparing(AssignedProcess::getAssignmentTime);
    }
}
