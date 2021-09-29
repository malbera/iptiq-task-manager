package com.iptiq.taskmanager.repository;

import com.iptiq.taskmanager.domain.Process;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProcessRepositoryFIFOTest {

    private ProcessRepository processRepository;

    @BeforeEach
    void before() {
        processRepository = new ProcessRepositoryFIFO(3);
    }

    @Test
    void addProcessTest() {
        processRepository.addProcess(new Process(Process.Priority.LOW));
        Process process1 = new Process(Process.Priority.HIGH);
        processRepository.addProcess(process1);
        Process process2 = new Process(Process.Priority.MEDIUM);
        processRepository.addProcess(process2);
        Process process3 = new Process(Process.Priority.HIGH);
        processRepository.addProcess(process3);

        List<Process> result = processRepository.listAllProcesses();

        assertThat(result).containsExactly(process1, process2, process3);
    }

    @Test
    void listAllProcessesTest() throws InterruptedException {
        Process process1 = new Process(Process.Priority.LOW);
        processRepository.addProcess(process1);
        Process process2 = new Process(Process.Priority.HIGH);
        processRepository.addProcess(process2);
        Process process3 = new Process(Process.Priority.MEDIUM);
        processRepository.addProcess(process3);

        List<Process> result = processRepository.listAllProcesses();

        assertThat(result).containsExactly(process1, process2, process3);
    }

    @Test
    void killProcessTest() {
        Process process1 = new Process(Process.Priority.LOW);
        processRepository.addProcess(process1);
        Process process2 = new Process(Process.Priority.HIGH);
        processRepository.addProcess(process2);
        Process process3 = new Process(Process.Priority.MEDIUM);
        processRepository.addProcess(process3);

        processRepository.killProcess(process1);
        List<Process> result = processRepository.listAllProcesses();

        assertThat(result).containsExactly(process2, process3);
    }

    @Test
    void killAllProcessesByGroupTest() {
        Process process1 = new Process(Process.Priority.LOW);
        processRepository.addProcess(process1);
        Process process2 = new Process(Process.Priority.HIGH);
        processRepository.addProcess(process2);
        processRepository.addProcess(new Process(Process.Priority.MEDIUM));
        processRepository.addProcess(new Process(Process.Priority.MEDIUM));

        processRepository.killAllProcessesByGroup(Process.Priority.MEDIUM);
        List<Process> result = processRepository.listAllProcesses();

        assertThat(result).containsExactly(process1, process2);
    }

    @Test
    void killAllProcessesTest() {
        processRepository.addProcess(new Process(Process.Priority.LOW));
        processRepository.addProcess(new Process(Process.Priority.HIGH));
        processRepository.addProcess(new Process(Process.Priority.MEDIUM));
        processRepository.addProcess(new Process(Process.Priority.HIGH));

        processRepository.killAllProcesses();
        List<Process> result = processRepository.listAllProcesses();

        assertThat(result).isEmpty();
    }
}
