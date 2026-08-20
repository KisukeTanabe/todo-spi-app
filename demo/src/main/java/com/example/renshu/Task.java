package com.example.renshu;
public class Task {

private String title;
private boolean done;

public Task(String title, boolean done) {

this.title = title;

this.done = done;

}

public String getTitle() {

return title;

}

public boolean isDone() {

return done;

}

}