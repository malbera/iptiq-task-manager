package com.iptiq.taskmanager.service;

import com.iptiq.taskmanager.domain.Process;
import com.iptiq.taskmanager.domain.Process.Priority;
import com.iptiq.taskmanager.repository.ProcessRepository;

import java.util.List;

public class TaskManagerServiceImpl implements TaskManagerService {

    private final ProcessRepository processRepository;

    public TaskManagerServiceImpl(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    @Override
    public void addProcess(Process process) {
        processRepository.addProcess(process);
    }

    @Override
    public List<Process> listAllProcesses() {
        return processRepository.listAllProcesses();
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
