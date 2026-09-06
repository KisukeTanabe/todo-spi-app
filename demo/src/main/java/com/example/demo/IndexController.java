package com.example.demo;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;



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

/*DBを使い実装しました*/
@Controller
public class IndexController {
    
    // Todoを保存するリスト
    private final TodoRepository repository;

    private final UserRepository userRepository;
    // ランダム値生成用
private final Random random = new Random();

public IndexController(TodoRepository repository, UserRepository userRepository) {
this.repository = repository;
this.userRepository = userRepository;

}

/**
* トップページ表示
* ・ランダム応援メッセージ表示
* ・時間帯に応じた挨拶表示
* ・締切間近のTodo通知表示
*/
@GetMapping("/")

public String showMotivation(Model model, HttpSession session) {

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
LocalDate lastSpiDate =(LocalDate) 
session.getAttribute("lastSpiDate");

// 締切24時間前〜締切1時間後のTodoを通知
LocalDateTime nowDate = LocalDateTime.now();

for(Todo todo : repository.findAll()){
    if (!todo.isCompleted() && nowDate.isAfter(todo.getDeadline().minusHours(1))&& nowDate.isBefore(todo.getDeadline())) 
    {
        notice += "【通知】"
        + todo.getTask()
        + " は締切まで1日です<br>";
    }
}

if (lastSpiDate != null &&
    lastSpiDate.isBefore(LocalDate.now().minusDays(3))) {
        notice += "【通知】SPI学習が3日間記録されていません<br>";
    }

session.setAttribute(
    "lastSpiDate",
    LocalDate.now());


String currentTime = LocalDateTime.now()
.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));

model.addAttribute("currentTime", currentTime);

model.addAttribute("notice", notice);
model.addAttribute("greeting", greeting);
model.addAttribute("todos", repository.findAll());
model.addAttribute(
    "userNotice",
    session.getAttribute("notice"));

String username = userRepository
    .findFirstByOrderByIdAsc()
    
    .map(User::getUsername)
    
    .orElse("ゲスト");
    model.addAttribute("username", username);

long incompleteCount = repository.findAll()
.stream()
.filter(todo -> !todo.isCompleted())
.count();

model.addAttribute("incompleteCount", incompleteCount);
model.addAttribute("studyDays", 35);

    return "index";
}

@GetMapping("/spi")

public String showSpi(Model model, HttpSession session) {
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
    System.out.println("件数=" + repository.findAll().size());
    model.addAttribute("todos", repository.findAll());

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
        Todo todo =
        new Todo(task, LocalDateTime.parse(deadline));
        repository.save(todo);
        System.out.println("件数：" + repository.findAll().size());


        return "redirect:/todo";
    }

/**
* Todo完了処理
*/
@PostMapping("/todo/complete")
public String completeTodo(@RequestParam long id, HttpSession session) {
    
    // 指定されたTodoを完了状態にする
    Todo todo = repository.findById(id).orElse(null);

    if (todo != null) {
        todo.setCompleted(true);
        repository.save(todo);
        session.setAttribute(
            "notice",
            "タスク完了おめでとうございます！");
        }

    return "redirect:/todo";
}

/**
* Todo削除処理
*/
@PostMapping("/todo/delete")
public String deleteTodo(@RequestParam long id) {
    
    // 指定されたTodoを削除
    repository.deleteById(id);

    return "redirect:/todo";
}

}


