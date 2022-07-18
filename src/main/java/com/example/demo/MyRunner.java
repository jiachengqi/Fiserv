package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    RestService restService;

    @Override
    public void run(String... args) {
        System.out.println("Enter word!");
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String input = scanner.next();
            System.out.println(processInput(input));
        }
    }

    public String processInput(String input) {
        int count = 0;
        String responseText = restService.getPostsAsString(input);

        if (responseText != null) {
            count += restService.count(responseText, input);
            return "The occurrence of the word " + input + " is " + count;
        }
        return "Invalid input! It should be an active topic on Wikipedia";
    }
}
