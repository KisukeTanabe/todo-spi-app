package com.example;

public class Book {
     String title;
     int price;

Book(String title, int price) {
    this.title = title;
    this.price = price;
    
}

void showInfo() {
    System.out.println("題名：" + title);
    System.out.println("価格：" + price);

}
void discount(int amount) {
    price = price - amount;
    System.out.println("値引き後価格：" + price);
}

public static void main(String[] args) {
   Book book1 = new Book("Java入門", 3000);
   book1.showInfo();

   book1.discount(500);

   Book book2 = new Book("python入門", 2000);
   book2.showInfo();

}
}


