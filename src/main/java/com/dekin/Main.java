package com.dekin;

import java.io.IOException;
import java.util.Map;

import static com.dekin.FindCountWord.*;
import static com.dekin.TranslateService.readAndWriteSentence;


public class Main {

    // my local path
    private static final String PATH = "D:\\Reviews.csv";
    private static final int COLUMN_TEXT = 9;
    private static final int COLUMN_PRODUCT_ID = 1;
    private static final int PROFILE_NAME = 3;


    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            //Finding 1000 most active users (profile names)
            Map<String, Integer> activeUsers = searchCountWords(PROFILE_NAME, PATH);
            show(activeUsers);
            clearMap();

            //Finding 1000 most commented food items (item ids).
            Map<String, Integer> commentedFood = searchCountWords(COLUMN_PRODUCT_ID, PATH);
            System.out.println();
            show(commentedFood);
            clearMap();

            //Finding 1000 most used words in the reviews
            Map<String, Integer> usedWords = searchCountWords(COLUMN_TEXT, PATH);
            System.out.println();
            show(usedWords);
        }
        if (args.length > 0 && args[0].equals("translate=true")) {
            readAndWriteSentence("ru", PATH);
        }
    }
}
