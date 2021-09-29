package com.iptiq.taskmanager.service;

import com.iptiq.taskmanager.domain.Process;
import com.iptiq.taskmanager.domain.Process.Priority;

import java.util.List;

public interface TaskManagerService {

    void addProcess(Process process);
    List<Process> listAllProcesses();
    void killProcess(Process process);
    void killAllProcessesByGroup(Priority priority);
    void killAllProcesses();

}
