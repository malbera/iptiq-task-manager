package com.iptiq.taskmanager.service;

import com.iptiq.taskmanager.domain.Process;
import com.iptiq.taskmanager.domain.Process.Priority;
import com.iptiq.taskmanager.repository.ProcessRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TaskManagerServiceImplTest {

    @Mock
    private ProcessRepository processRepository;
    @Mock
    private Process process;
    @InjectMocks
    private TaskManagerServiceImpl taskManagerService;

    @Test
    void addProcessTest() {
        taskManagerService.addProcess(process);

        verify(processRepository).addProcess(process);
    }

    @Test
    void listAllProcessesTest() {
        List<Process> processes = Collections.singletonList(process);
        when(processRepository.listAllProcessesSorted()).thenReturn(processes);

        List<Process> result = taskManagerService.listAllProcesses();

        assertThat(result).isSameAs(processes);
    }

    @Test
    void killProcessTest() {
        taskManagerService.killProcess(process);

        verify(processRepository).killProcess(process);
    }

    @Test
    void killAllProcessesByGroupTest() {
        taskManagerService.killAllProcessesByGroup(Priority.HIGH);

        verify(processRepository).killAllProcessesByGroup(Priority.HIGH);
    }

    @Test
    void killAllProcessesTest() {
        taskManagerService.killAllProcesses();

        verify(processRepository).killAllProcesses();
    }
}
