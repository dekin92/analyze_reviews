package com.dekin;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TranslateService {

    private static final int COLUMN_TEXT = 9;

    private static final String URL = "https://translation.googleapis.com/language/translate/v2?key=";
    private static final String API_KEY = "API_KEY";
    private static final Logger LOGGER = Logger.getLogger(TranslateService.class.getName());
    private static final String PATH = "D:\\Reviews.csv";
    private static int LIMIT = 1000;

    static void readAndWriteSentence(final String outputLang, String path) throws IOException {
        String arrayLine;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((arrayLine = br.readLine()) != null) {
                // need to put Thread for parallel separate and send to translate
                    String line = arrayLine.split(",")[COLUMN_TEXT];
                    line = line.replaceAll("\\<(/?[^\\>]+)\\>", "");
                    substringByThousand(line, outputLang);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void substringByThousand(String line, String outputLang) {
        if (line.length() < LIMIT) {
            translateLine(line, outputLang);
            return;
        }
        if (line.lastIndexOf('.') + 1 == LIMIT) {
            translateLine(line, outputLang);
            return;
        }

        String substringByLimit;
        String remainder = line;
        List<String> listLine = new ArrayList<>();
        int countChar = 0;
        while (countChar <= line.length()) {

            if (LIMIT >= remainder.length()) {
                listLine.add(remainder);
                break;
            } else {
                substringByLimit = remainder.substring(0, LIMIT);
                //get last dot from remainder ()
                int indexLastDot = substringByLimit.lastIndexOf('.');
                //split string from zero to last dot
                substringByLimit = substringByLimit.substring(0, indexLastDot + 1);
                //save remainder from index last dot to line length
                remainder = remainder.substring(indexLastDot + 1, remainder.length());
                //put into collection for translate
                listLine.add(substringByLimit);
                //count all passed char
                countChar = countChar + indexLastDot;
            }
        }
        //translate line by google api
        for (String lineSub : listLine) {
            translateLine(lineSub, outputLang);
        }
    }

    private static void translateLine(String text, String target) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("q", text);
        map.add("target", target);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> exchange = restTemplate.postForEntity(URL + API_KEY, request, String.class);
        LOGGER.info("HTTP Status code " + exchange.getStatusCode() +
                ". Rest invocation result for command 'https://translation.googleapis.com/language/translate/v2?key=API_KEY': " + exchange.getBody());
    }

}
