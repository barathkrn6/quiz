package com.saarthi.quiz;

import com.opencsv.CSVReader;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestClass {

    static Map<String, Integer> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
        map.put("a", 0);
        map.put("b", 1);
        map.put("c", 2);
        map.put("d", 3);
        PrintStream o = new PrintStream(new File("/Users/barath.karunakaran/Desktop/output.txt"));

        // Store current System.out before assigning a new value
        PrintStream console = System.out;
        System.setOut(o);
        // readFile(new File("/Users/barath.karunakaran/Downloads/Mix_30_que.csv"));
        // readFile(new File("/Users/barath.karunakaran/Documents/Saarathi/questions/100_que.csv"));
        // Set<String> set = readFile1();
        readFile();
        // System.out.println(new TestClass().createQuiz(10000));
        System.out.println("done");
    }

    public static Set<String> readFile1() throws Exception {
        CSVReader reader = new CSVReader(new FileReader("/Users/barath.karunakaran/Documents/Saarathi/questions/100_que" +
                ".csv"));
        String[] splitLine;
        Set<String> set = new HashSet<>();
        while ((splitLine = reader.readNext()) != null) {
            String existingId = splitLine[0];
            set.add(existingId);
        }
        return set;
    }

    public static void readFile() throws Exception {
        CSVReader reader = new CSVReader(new FileReader("/Users/barath.karunakaran/Desktop/10Sep.csv"));
        String[] splitLine;
        int count = 0;
        int quizId = 200;
        String quizResponse = createQuiz(quizId);
        JSONObject quizObj = new JSONObject(quizResponse);
        Integer quizIdd = quizObj.getInt("id");

        while ((splitLine = reader.readNext()) != null) {
            try {
                // System.out.println();
                String id = splitLine[0];
                String name = splitLine[1].replaceAll("\"", "").trim();
                String options = splitLine[2].replaceAll("\"", "").replaceAll(",", " ").trim() + "," +
                        splitLine[3].replaceAll("\"", "").replaceAll(",", " ").trim() + "," +
                        splitLine[4].replaceAll("\"", "").replaceAll(",", " ").trim() + "," +
                        splitLine[5].replaceAll("\"", "").replaceAll(",", " ").trim();
                String explanation = splitLine[6].replaceAll("\"", "").trim();
                Integer option = map.get(splitLine[7].replaceAll("\"", "").trim().toLowerCase());

                // if (!set.contains(id)) {
                if (option != null) {
                    count++;
                    System.out.println(id);

                    if (count % 10 == 0) {
                        quizId++;
                        quizResponse = createQuiz(quizId);
                        quizObj = new JSONObject(quizResponse);
                        quizIdd = quizObj.getInt("id");
                    }

                    JSONObject obj = new JSONObject();
                    obj.put("correct_option", option);
                    obj.put("name", name);
                    obj.put("options", options);
                    obj.put("points", 10);
                    obj.put("quiz_id", quizIdd);
                    obj.put("explanation", explanation);

                    System.out.println(obj);

                    /*URL url = new URL("https://barath-quiz-telegram.herokuapp.com/api/questions/");
                    // URL url = new URL("http://localhost:8080/api/questions/");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json; utf-8");
                    con.setRequestProperty("Accept", "application/json");
                    con.setDoOutput(true);

                    try (OutputStream os = con.getOutputStream()) {
                        byte[] input = obj.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    OutputStream os = con.getOutputStream();
                    os.flush();
                    os.close();
                    // For POST only - END

                    int responseCode = con.getResponseCode();
                    System.out.println("POST Response Code :: " + responseCode);*/

                }
                // }
            } catch (Exception e) {

            }
        }
    }

    public static String createQuiz(Integer quizNo) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("description", "Desc-" + quizNo);
        obj.put("name", "Quiz-Name-" + quizNo);

        URL url = new URL("https://barath-quiz-telegram.herokuapp.com/api/quiz/");
        // URL url = new URL("http://localhost:8080/api/questions/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = obj.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        OutputStream os = con.getOutputStream();
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        InputStream inputStream;
        if (200 <= responseCode && responseCode <= 299) {
            inputStream = con.getInputStream();
        } else {
            inputStream = con.getErrorStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder response = new StringBuilder();
        String currentLine;

        while ((currentLine = in.readLine()) != null)
            response.append(currentLine);

        in.close();

        System.out.println("===============================" + response.toString());
        return response.toString();
    }
}
