package com.iptiq.taskmanager.domain;

import java.time.LocalDateTime;

public class AssignedProcess {

    private final Process process;
    private final LocalDateTime assignmentTime;

    public AssignedProcess(Process process) {
        this.process = process;
        this.assignmentTime = LocalDateTime.now();
    }

    public Process getProcess() {
        return process;
    }

    public LocalDateTime getAssignmentTime() {
        return assignmentTime;
    }

}
