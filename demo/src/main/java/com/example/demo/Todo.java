package com.example.demo;

import java.time.LocalDateTime;

public class Todo {

private String task;

private LocalDateTime deadline;
private boolean completed;

public Todo(String task, LocalDateTime deadline) {
    this.task = task;
    this.deadline = deadline;
    this.completed = false;
}

public String getTask() {
    return task;
}

public LocalDateTime getDeadline() {
    return deadline;
}

public boolean isCompleted() {
    return completed;
}

public void setCompleted(boolean completed) {
    this.completed = completed;
}

}