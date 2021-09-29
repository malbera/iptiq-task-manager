package com.iptiq.taskmanager.domain;

import java.util.Objects;
import java.util.UUID;

public class Process {

    private final String id;
    private final Priority priority;

    public Process(Priority priority) {
        this.id = UUID.randomUUID().toString();
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public Priority getPriority() {
        return priority;
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return Objects.equals(id, process.id) &&
                priority == process.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priority);
    }
}
