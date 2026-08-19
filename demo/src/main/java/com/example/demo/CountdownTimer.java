package com.example.demo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CountdownTimer {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("カウントダウンする秒数を入力してください: ");
            int seconds = scanner.nextInt();
            scanner.nextLine();

            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = startTime.plusSeconds(seconds);

            System.out.println("カウントダウン開始...");

            while (LocalDateTime.now().isBefore(endTime)){
                long remainingSeconds = Duration.between(LocalDateTime.now(), endTime).getSeconds();
                System.out.println("残り時間: " + remainingSeconds + "秒");
                Thread.sleep(1000);
            }
            System.out.println("カウントダウン終了！");

        }catch (InputMismatchException e) {
            System.err.println("整数を入力してください。");
        }catch (Exception e){
            System.err.println("エラーが発生しました: " + e.getMessage());
        }
    }
}
