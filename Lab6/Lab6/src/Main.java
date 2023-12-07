import grammar.Grammar;
import parser.Parser;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Grammar grammar;
        Parser parser;
        try {
            while (true) {
                System.out.println("Enter an option: ");
                System.out.println("1. g1");
                System.out.println("2. g2");
                System.out.println("x. Exit");
                System.out.print("> ");
                String option = scanner.nextLine();

                switch (option) {
                    case "1" -> {
                        grammar = Grammar.fromFile("g1.txt");
                        parser = new Parser(grammar);
                        System.out.println(grammar);
                        System.out.println("First: " + parser.getFirstSet());
                        System.out.println("Follow: " + parser.getFollowSet());
                    }
                    case "2" -> {
                        grammar = Grammar.fromFile("g2.txt");
                        parser = new Parser(grammar);
                        System.out.println(grammar);
                        System.out.println("First: " + parser.getFirstSet());
                        System.out.println("Follow: " + parser.getFollowSet());
                    }
                    case "x" -> {
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid option!");
                }
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}