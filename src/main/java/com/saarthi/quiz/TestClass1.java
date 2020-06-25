package com.saarthi.quiz;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class TestClass1 {
    public static void main(String[] args) throws Exception {
        try {
            File myObj = new File("/Users/barath.karunakaran/Desktop/output.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains("===============================")) {
                    // System.out.println(data);
                } else if (data.contains("correct_option")) {
                    JSONObject obj = new JSONObject(data);
                    String fileName = obj.get("quiz_id").toString();
                    new TestClass1().writeContent("/Users/barath.karunakaran/Documents/Saarathi/newquest/" + fileName + ".txt", data);
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeContent(String filePath, String data) throws Exception {
        File file = new File(filePath);
        FileWriter fr = new FileWriter(file, true);
        fr.write(data + "\n");
        fr.close();
    }
}
