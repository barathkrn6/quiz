package com.saarthi.quiz;

import com.opencsv.CSVReader;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TestClass {

    static Map<String, Integer> map = new HashMap<>();
    public static void main(String[] args) throws Exception {
        map.put("a", 0);
        map.put("b", 1);
        map.put("c", 2);
        map.put("d", 3);
        readFile(new File("/Users/barath.karunakaran/Downloads/Mix_30_que.csv"));
    }

    public static void readFile(File file) throws Exception {
        CSVReader reader = new CSVReader(new FileReader("/Users/barath.karunakaran/Downloads/Mix_30_que.csv"));
        String [] splitLine;
        while ((splitLine = reader.readNext()) != null) {
            String name = splitLine[1].replace("\"", "").trim();
            String options = splitLine[2].replace("\"", "").trim() + "," + splitLine[3].replace("\"", "").trim()
                    + "," + splitLine[4].replace("\"", "").trim() + "," + splitLine[5].replace("\"", "").trim();
            Integer option = map.get(splitLine[7].replace("\"", "").trim().toLowerCase());

            if (option != null) {
                JSONObject obj = new JSONObject();
                obj.put("correct_option", option);
                obj.put("name", name);
                obj.put("options", options);
                obj.put("points", 10);
                obj.put("quiz_id", 1);

                System.out.println(obj);

                // URL url = new URL("https://barath-quiz-telegram.herokuapp.com/api/questions/");
                URL url = new URL("http://localhost:8080/api/questions/");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = obj.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                OutputStream os = con.getOutputStream();
                os.flush();
                os.close();
                // For POST only - END

                int responseCode = con.getResponseCode();
                System.out.println("POST Response Code :: " + responseCode);
            }
        }
    }
}
