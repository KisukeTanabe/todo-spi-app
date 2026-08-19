package com.example.demo;

import java.time.LocalDateTime;

/**
* Todoクラス
* タスク名、締切日時、完了状態を管理する
*/
public class Todo {
    // タスク名
    private String task;

    // 締切日時
    private LocalDateTime deadline;

    // 完了状態
    private boolean completed;
    
    /**
    * コンストラクタ
    * Todo作成時は未完了(false)で初期化
    */
    public Todo(String task, LocalDateTime deadline) {
        this.task = task;
        this.deadline = deadline;
        this.completed = false;
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