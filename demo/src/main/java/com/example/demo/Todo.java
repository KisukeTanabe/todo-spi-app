package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
* Todoクラス
* タスク名、締切日時、完了状態を管理する
*/

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // タスク名
    private String task;

    // 締切日時
    private LocalDateTime deadline;

    // 完了状態
    private boolean completed;
    
    public Todo() {
    }

    /**
    * コンストラクタ
    * Todo作成時は未完了(false)で初期化
    */
    public Todo(String task, LocalDateTime deadline) {
        this.task = task;
        this.deadline = deadline;
        this.completed = false;
    }

    public Long getId() {
        return id;
    }

    /**
    * タスク名取得
    */
    public String getTask() {

        return task;
    }

    /**
    * 締切日時取得
    */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
    * 完了状態取得
    */
    public boolean isCompleted() {
        return completed;
    }
    
    /**
    * 完了状態更新
    */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}