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

@Controller
public class index {
private List<Todo> todoList = new ArrayList<>();
    
@GetMapping("/")



public String showMotivation(Model model) {

String[] messages = {
    "頑張ろう！",
    "あと少し！",
    "今日も一歩前進！",
    "諦めるな！"
};

Random random = new Random();

int index = random.nextInt(messages.length);

String message = messages[index];

model.addAttribute("message", message);

LocalTime now = LocalTime.now();

String greeting;

if (now.getHour() < 12) {

greeting = "おはようございます、今日も頑張りましょう";

} else if (now.getHour() < 18) {

greeting = "こんにちは、もうお昼ですね";

} else {

greeting = "こんばんは、あともう一息です";

}

model.addAttribute("message", message);


String notice = "";

for(Todo todo : todoList){

LocalDateTime nowDate = LocalDateTime.now();

if(nowDate.isAfter(todo.getDeadline().minusHours(1))

&& nowDate.isBefore(todo.getDeadline().plusHours(23))) {
    notice += todo.getTask()
    + " の締切が近づいています！<br>";
}

}

model.addAttribute("notice", notice);

model.addAttribute("greeting", greeting);

model.addAttribute("todos", todoList);

return "index";
}

@GetMapping("/spi")

public String showSpi(Model model) {
    Random rand = new Random();
    int type = rand.nextInt(5);
String question = "";

String answer = "";

String explanation = "";

switch(type){
    case 0:
        int a = rand.nextInt(50) + 1;
        int b = rand.nextInt(50) + 1;
        question = a + " + " + b + " = ?";
        answer = String.valueOf(a + b);
        explanation = "足し算なので " + a + " + " + b + " を計算します。";

break;

case 1:

a = rand.nextInt(50) + 50;

b = rand.nextInt(30) + 1;

question = a + " - " + b + " = ?";

answer = String.valueOf(a - b);

explanation = "引き算を行います。";

break;

case 2:

a = rand.nextInt(10) + 1;

b = rand.nextInt(10) + 1;

question = a + " × " + b + " = ?";

answer = String.valueOf(a * b);

explanation = "掛け算です。";

break;

case 3:

b = rand.nextInt(9) + 2;

a = b * (rand.nextInt(10) + 1);

question = a + " ÷ " + b + " = ?";

answer = String.valueOf(a / b);

explanation = "割り算です。";

break;

case 4:

a = rand.nextInt(30) + 10;

question = "ある数を2倍して" + a +

"を足すと" + (a + 20) +

"になります。ある数は？";

answer = "10";

explanation =

"(a+20)-a=20 より、元の数の2倍は20。したがって10。";

break;

}

model.addAttribute("question", question);

model.addAttribute("answer", answer);

model.addAttribute("explanation", explanation);

return "spi";

}

@GetMapping("/todo")

public String showTodo(Model model) {

System.out.println("件数=" + todoList.size());
model.addAttribute("todos", todoList);

return "todo";

}

@PostMapping("/todo/add")

public String addTodo(
    @RequestParam String task,
    @RequestParam String deadline) {

System.out.println("追加：" + task);

System.out.println("締切：" + deadline);

todoList.add(

new Todo(task, LocalDateTime.parse(deadline)));

System.out.println("件数：" + todoList.size());

return "redirect:/todo";

}
@PostMapping("/todo/complete")

public String completeTodo(@RequestParam int index) {
    todoList.get(index).setCompleted(true);
    return "redirect:/todo";
}

@PostMapping("/todo/delete")

public String deleteTodo(
    @RequestParam int index) 
    {todoList.remove(index);
    return "redirect:/todo";
}

}


