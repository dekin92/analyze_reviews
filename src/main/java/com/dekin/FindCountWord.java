package com.dekin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class FindCountWord {

    private static final int LIMIT = 1000;
    private static final int COLUMN_TEXT = 9;
    private static Map<String, Integer> map = new HashMap<>();

    static void clearMap() {
        map = new HashMap<>();
    }

    /**
     * Read the file line by column and divide words by ",",  after check if there is a word in the map, if there is,
     * then improve the counter and put in map, if not - put in map. Using Time O(N), Space O(N)
     *
     * @return
     */
    static Map<String, Integer> searchCountWords(int column, String path) {
        String arrayLine;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((arrayLine = br.readLine()) != null) {
                String line = arrayLine.split(",")[column];
                //get two column (Summary and Text) and substring all column except the last two
                if (column == 9) {
                    line = arrayLine.substring(arrayLine.indexOf(',', 1 +
                            arrayLine.indexOf(',', 1 + arrayLine.indexOf(',', 1 +
                                    arrayLine.indexOf(',', 1 + arrayLine.indexOf(',', 1 +
                                            arrayLine.indexOf(',', 1 + arrayLine.indexOf(',', 1 +
                                                    arrayLine.indexOf(',')))))))) + 1, arrayLine.length());
                }
                if (line.equals("ProfileName") || line.equals("ProductId") || line.equals("Text")) {
                    continue;
                }
                if (column == COLUMN_TEXT) {
                    line = line.replaceAll("\\<(/?[^\\>]+)\\>", "");
                    line = line.replaceAll("[^a-zA-Z ]", "");
                    String[] split = line.split(" ");
                    for (String word : split) {
                        putElements(word.toLowerCase());
                    }
                } else {
                    putElements(line.toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .limit(LIMIT)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private static void putElements(String line) {
        if (map.containsKey(line)) {
            map.put(line, map.get(line) + 1);
        } else {
            map.put(line, 1);
        }
    }

    /**
     * Display result
     *
     * @param words
     */
    static void show(Map<String, Integer> words) {
        words.forEach((k, v) -> System.out.println(k + " " + v));
    }
}
