package com.fit;

import java.util.Random;
import java.util.Scanner;

public class Target {
  public static void main(String[] args) {
    Random random = new Random();
    int target = random.nextInt(100);
    Scanner scanner = new Scanner(System.in);
    int guess = target + 1;

    System.out.println("Guess from 0 to 100");

    while (target != guess) {
      guess = scanner.nextInt();

      if (guess > target) {
        System.out.println("Lower");
      } else if (guess < target) {
        System.out.println("Greater");
      }
    }

    System.out.println("Exactly");
  }
}
