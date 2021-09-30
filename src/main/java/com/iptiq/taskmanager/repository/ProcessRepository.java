package com.iptiq.taskmanager.repository;

import com.iptiq.taskmanager.domain.AssignedProcess;
import com.iptiq.taskmanager.domain.Process;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface ProcessRepository {

    void addProcess(Process process);

    List<AssignedProcess> listAllAssignedProcesses();

    void killProcess(Process process);

    void killAllProcessesByGroup(Process.Priority priority);

    void killAllProcesses();

    default List<Process> listAllProcessesSorted() {
        return this.listAllAssignedProcesses().stream()
                .sorted(Comparator.comparing(AssignedProcess::getAssignmentTime)
                        .thenComparing(ap -> ap.getProcess().getId())
                )
                .map(AssignedProcess::getProcess)
                .collect(Collectors.toList());
    }
}
