package com.example.renshu;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class GuessNumberGame {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)){
            Random random = new Random();
            int answer = random.nextInt(100) + 1;
            int guess = 0;
            System.out.println("1から100までの数字を当ててみてください。");

            while (guess != answer) {
                System.out.print("数字を入力してください: ");
                guess = scanner.nextInt();
                scanner.nextLine();

                                if (guess < answer) {
                                    System.out.println("もっと大きい数字です！");
                                } else if (guess > answer) {
                                    System.out.println("もっと小さい数字です！");
                                } else {
                                    System.out.println("正解です！");
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.err.println("整数を入力してください！");
                        } catch (Exception e) {
                            System.err.println("エラーが発生しました:" + e.getMessage());
                        }
                    }
                }
