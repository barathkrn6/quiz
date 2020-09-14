package com.saarthi.quiz;

import org.json.JSONObject;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestClass2 {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /*Calendar c = Calendar.getInstance();
        List<String> list = new ArrayList<>();
        list.add("12-pm-test");
        list.add("2-pm-test");
        list.add("4-pm-test");
        int counter = 0;*/
        for (int i = 335619; i <= 336556; i++) {
            File file = new File("/Users/barath.karunakaran/Documents/Saarathi/newquest1/" + i + ".txt");
            readFile(file);

            /*(String examText = "12-pm-test";
            int rem = counter % 3;
            if (rem == 0) {
                c.add(Calendar.DATE, 1);
                examText = list.get(0);
            } else if (rem == 1) {
                examText = list.get(1);
            } else if (rem == 2) {
                examText = list.get(2);
            }
            String updatedDate = sdf.format(c.getTime());
            // System.out.println(updatedDate);
            c.setTime(sdf.parse(updatedDate));
            // System.out.println(c.getTime());

            String query = "INSERT INTO quiz_schedule(id, chat_id, name, quiz_id, token, schedule_date) VALUES " +
                    "(" + i + ", '-1001166690884~-1001381771846', '" + examText + "', '" + i +
                    "', '1280579534:AAFs13tSRQEsfmC9BQG_z5awPIQ_yAxgNP0', '" + updatedDate + "');";

            System.out.println(query);
            counter++;*/
        }
    }

    public static void readFile(File file) throws Exception {
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            JSONObject obj = new JSONObject(data);
            try {
                // System.out.println(data);

                URL url = new URL("http://localhost:8080/api/questions/");
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
