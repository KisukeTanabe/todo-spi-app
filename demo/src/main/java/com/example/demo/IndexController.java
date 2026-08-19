package com.example.demo;

import java.util.Random;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
* 就職活動支援Webアプリ
*
* 機能
* 1. ランダム応援メッセージ表示
* 2. 時間帯別挨拶表示
* 3. SPI演習問題自動生成
* 4. Todo管理機能
* 5. 締切通知機能
*
* Spring Bootを使用して作成
*/

/*DBをまだ習っていないのでListで実装しました*/
@Controller
public class IndexController {
    // Todoを保存するリスト
    private List<Todo> todoList = new ArrayList<>();
    // ランダム値生成用
private final Random random = new Random();
    
/**
* トップページ表示
* ・ランダム応援メッセージ表示
* ・時間帯に応じた挨拶表示
* ・締切間近のTodo通知表示
*/
@GetMapping("/")

public String showMotivation(Model model) {

String[] messages = {
    "頑張ろう！",
    "あと少し！",
    "今日も一歩前進！",
    "諦めるな！"
};

// 応援メッセージをランダムに選択
int randomIndex = random.nextInt(messages.length);
String message = messages[randomIndex];
model.addAttribute("message", message);

// 現在時刻から挨拶を決定
LocalTime now = LocalTime.now();
String greeting;

if (now.getHour() < 12) {
    greeting = "おはようございます、今日も頑張りましょう";
} else if (now.getHour() < 18) {
    greeting = "こんにちは、もうお昼ですね";
} else {
    greeting = "こんばんは、あともう一息です";
}

String notice = "";

// 締切24時間前〜締切1時間後のTodoを通知
LocalDateTime nowDate = LocalDateTime.now();

for(Todo todo : todoList){
    if(nowDate.isAfter(todo.getDeadline().minusHours(1))&& nowDate.isBefore(todo.getDeadline().plusHours(23))) 
    {
        notice += todo.getTask() + " の締切が近づいています！<br>";
    }
}

model.addAttribute("notice", notice);
model.addAttribute("greeting", greeting);
model.addAttribute("todos", todoList);

return "index";

}


@GetMapping("/spi")

public String showSpi(Model model) {
    int type = random.nextInt(5);
    String question = "";
    String answer = "";
    String explanation = "";
    
    switch(type){

        // 足し算問題
        case 0:
        int a = random.nextInt(50) + 1;
        int b = random.nextInt(50) + 1;
        question = a + " + " + b + " = ?";
        answer = String.valueOf(a + b);
        explanation = "足し算なので " + a + " + " + b + " を計算します。";
        break;

        // 引き算問題
        case 1:
            a = random.nextInt(50) + 50;
            b = random.nextInt(30) + 1;
            question = a + " - " + b + " = ?";
            answer = String.valueOf(a - b);
            explanation = "引き算を行います。";
            break;

            // 掛け算問題
            case 2:
                a = random.nextInt(10) + 1;
                b = random.nextInt(10) + 1;
                question = a + " × " + b + " = ?";
                answer = String.valueOf(a * b);
                explanation = "掛け算です。";
                break;

                // 割り算問題
                case 3:
                    b = random.nextInt(9) + 2;
                    a = b * (random.nextInt(10) + 1);
                    question = a + " ÷ " + b + " = ?";
                    answer = String.valueOf(a / b);
                    explanation = "割り算です。";
                    break;

                    // 方程式問題
                    case 4:
                        a = random.nextInt(30) + 10;
                        question = "ある数を2倍して" + a +
                        "を足すと" + (a + 20) +
                        "になります。ある数は？";
                        answer = "10";
                        explanation = "(a+20)-a=20 より、元の数の2倍は20。したがって10。";
                        break;

                    }
                    model.addAttribute("question", question);
                    model.addAttribute("answer", answer);
                    model.addAttribute("explanation", explanation);
                    
                    return "spi";
                
                }


/**
* Todo一覧画面表示
*/
@GetMapping("/todo")

public String showTodo(Model model) {
    System.out.println("件数=" + todoList.size());
    model.addAttribute("todos", todoList);

    return "todo";
}

/**
* Todo追加処理
*/
@PostMapping("/todo/add")
public String addTodo(
    @RequestParam String task,
    @RequestParam String deadline) {
        System.out.println("追加：" + task);
        System.out.println("締切：" + deadline);

        // Todoをリストに追加
        todoList.add(new Todo(task, LocalDateTime.parse(deadline)));
        System.out.println("件数：" + todoList.size());

        return "redirect:/todo";
    }

/**
* Todo完了処理
*/
@PostMapping("/todo/complete")
public String completeTodo(@RequestParam int index) {

    // 指定されたTodoを完了状態にする
    todoList.get(index).setCompleted(true);

    return "redirect:/todo";
}

/**
* Todo削除処理
*/
@PostMapping("/todo/delete")
public String deleteTodo(@RequestParam int index) {
    
    // 指定されたTodoを削除
    todoList.remove(index);

    return "redirect:/todo";
}

}


