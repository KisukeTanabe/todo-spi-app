package com.example;

public class Student {
    String name;
    int age;



Student(String name, int age) {
    this.name = name;
    this.age = age;

}

void showInfo() {
    System.out.println("名前：" + name);
    System.out.println("年齢：" + age);

}

public static void main(String[] args) {
    Student student = new Student("田邊", 20);
    student.showInfo();

}

}