package com.iptiq.taskmanager.repository;

import com.iptiq.taskmanager.domain.Process;

import java.util.List;

public interface ProcessRepository {

    void addProcess(Process process);
    List<Process> listAllProcesses();
    void killProcess(Process process);
    void killAllProcessesByGroup(Process.Priority priority);
    void killAllProcesses();

}
