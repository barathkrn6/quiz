package com.saarthi.quiz;

import org.json.JSONObject;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class TestClass2 {
    public static void main(String[] args) throws Exception {
        for (int i = 1476; i <= 1533; i++) {
            File file = new File("/Users/barath.karunakaran/Documents/Saarathi/newquest/" + i + ".txt");
            readFile(file);
        }
    }

    public static void readFile(File file) throws Exception {
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            JSONObject obj = new JSONObject(data);
            try {
                // System.out.println(data);

                URL url = new URL("https://barath-quiz-telegram.herokuapp.com/api/questions/");
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
                System.out.println("POST Response Code :: " + responseCode);
            } catch (Exception e) {
                System.out.println("ERROR :::::: " + data);
            }
        }
    }
}
