package com.jano;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int turnCounter = 1;
        int codeLength;
        int charNumber;
        String guess;
        String code;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the length of the secret code: ");
        String input = scanner.nextLine();
        try {
            codeLength = Integer.parseInt(input);
            if (codeLength > 36) {
                System.out.println("Error: can't generate a secret code because there aren't enough unique characters.");
            } else {
                System.out.println("Input the number of possible symbols in the code: ");
                charNumber = scanner.nextInt();
                if (charNumber > 36) {
                    System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                } else if (charNumber < codeLength || charNumber < 10) {
                    System.out.println("Error: it's not possible to generate a code with a length of " + codeLength + " with " + charNumber + " unique symbols.");
                } else {
                    preparingCodeMenu(codeLength, charNumber);
                    code = randomGenerator(codeLength, charNumber);
                    scanner.nextLine();
                    do {
                        System.out.println("\nTurn " + turnCounter + ":");
                        guess = scanner.nextLine();
                        printGrade(code, guess);
                        turnCounter++;
                    } while (!guess.equals(code));
                }
            }
        } catch (Exception e) {
            System.out.println("Error: \"" + input + "\" isn't a valid number.");
        }
    }

    public static void preparingCodeMenu(int codeLength, int charNumber) {
        char range = 'a';
        int temp = charNumber - 10;
        for (int i = 1; i < temp; i++) {
            range++;
        }
        System.out.print("The secret is prepared: ");
        if (temp <= 0) {
            for (int i = 0; i < codeLength; i++) {
                System.out.print('*');
            }
            System.out.print(" (0-9).");
        } else {
            for (int i = 0; i < codeLength; i++) {
                System.out.print('*');
            }
            System.out.print(" (0-9, a-" + range + ").");
        }
        System.out.println("\nOkay, let's start a game!");

    }

    public static String randomGenerator(int length, int characters) {
        int range = characters - 10;
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        while (code.length() < length) {
            double randomClock = Math.random();
            if (range > 0) {
                if (randomClock > 0.5) {
                    int randomNum = random.nextInt(10);
                    String temp = Integer.toString(randomNum);
                    if (!code.toString().contains(temp)) {
                        code.append(randomNum);
                    }
                } else {
                    char randomChar = (char) (random.nextInt(range) + 'a');
                    String temp = Character.toString(randomChar);
                    if (!code.toString().contains(temp)) {
                        code.append(randomChar);
                    }
                }
            } else {
                int randomNum = random.nextInt(10);
                String temp = Integer.toString(randomNum);
                if (!code.toString().contains(temp)) {
                    code.append(randomNum);
                }
            }
        }
        return code.toString();
    }

    public static void printGrade(String code, String guess) {
        int bullGrade = checkBull(code, guess);
        int cowGrade = checkCow(code, guess);
        if (bullGrade == code.length()) {
            System.out.println("Grade: " + bullGrade + " bulls");
            System.out.println("Congrats! The secret code is " + code + ".");
        } else if (bullGrade == 1 && cowGrade == 0) {
            System.out.println("Grade: 1 bull.");
        } else if (bullGrade == 0 && cowGrade == 1) {
            System.out.println("Grade: 1 cow.");
        } else if (bullGrade > 1 && cowGrade == 0) {
            System.out.println("Grade: " + bullGrade + " bulls.");
        } else if (bullGrade == 0 && cowGrade > 1) {
            System.out.println("Grade: " + cowGrade + " cows.");
        } else if (bullGrade > 1 && cowGrade == 1) {
            System.out.println("Grade: " + bullGrade + " bulls and 1 cow.");
        } else if (bullGrade == 1 && cowGrade > 1) {
            System.out.println("Grade: 1 bull and " + cowGrade + " cows.");
        } else if (bullGrade == 1 && cowGrade == 1 ) {
            System.out.println("Grade 1 bull and 1 cow.");
        } else if (bullGrade == 0 && cowGrade == 0) {
            System.out.println("Grade: None.");
        } else {
            System.out.println("Grade: " + bullGrade + " bulls and " + cowGrade + " cows.");
        }
    }

    private static int checkBull(String code, String guess) {
        int bull = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == code.charAt(i)) {
                bull++;
            }
        }
        return bull;
    }

    private static int checkCow(String code, String guess) {
        int cow = 0;
        for (int i = 0; i < code.length(); i++) {
            for (int j = 0; j < guess.length(); j++) {
                if (guess.charAt(i) == code.charAt(j)) {
                    cow++;
                }
            }
        }
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == code.charAt(i)) {
                cow--;
            }
        }
        return cow;
    }
}