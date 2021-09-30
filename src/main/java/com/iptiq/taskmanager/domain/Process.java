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

    public enum Priority {
        LOW(1),
        MEDIUM(10),
        HIGH(20);

        private final int priorityValue;

        Priority(int priorityValue) {
            this.priorityValue = priorityValue;
        }

        public int getPriorityValue() {
            return priorityValue;
        }
    }
}
