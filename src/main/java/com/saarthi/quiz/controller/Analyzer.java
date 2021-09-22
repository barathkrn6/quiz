package com.saarthi.quiz.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Analyzer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void setStyle(Row row, XSSFCellStyle style) {
        row.getCell(0).setCellStyle(style);
        row.getCell(1).setCellStyle(style);
        row.getCell(2).setCellStyle(style);
        row.getCell(3).setCellStyle(style);
        row.getCell(4).setCellStyle(style);
        row.getCell(5).setCellStyle(style);
    }

    public static boolean isInteger(Double N) {
        if (N % 1 == 0)
            return true;
        return false;
    }

    public void sendMail(String subjectLine) {
        String[] to = {"sree.starz@gmail.com"};
        String from = "barathkrn6@gmail.com";

        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.user", from);
        properties.put("mail.smtp.password", "1sabithaDS!");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            for (String t : to) {
                message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(t, false));
            }
            message.setSubject(subjectLine);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart attachmentPart = new MimeBodyPart();
            MimeBodyPart textPart = new MimeBodyPart();

            try {
                File f = new File(subjectLine);
                attachmentPart.attachFile(f);
                textPart.setText(subjectLine);
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);
            } catch (IOException e) {
                e.printStackTrace();
            }

            message.setContent(multipart);
            logger.info("sending...");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, "1sabithaDS!");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            // Transport.send(message);
            logger.info("Sent message successfully....");
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }

    @GetMapping("/mrng_scheduler")
    public void mrngScheduler() throws Exception {
        HttpClient client = HttpClients.custom().build();
        HttpUriRequest request = RequestBuilder.get()
                .setUri("https://www1.nseindia.com/live_market/dynaContent/live_watch/stock_watch/niftyStockWatch.json")
                .setHeader("X-Requested-With", "XMLHttpRequest")
                .setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                .build();
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        JSONObject obj = new JSONObject(responseString);
        JSONArray data = obj.getJSONArray("data");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setItalic(true);

        XSSFCellStyle styleHeader = workbook.createCellStyle();
        styleHeader.setFont(font);

        XSSFCellStyle styleRed = workbook.createCellStyle();
        styleRed.setFillForegroundColor(IndexedColors.RED.getIndex());
        styleRed.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFCellStyle styleGreen = workbook.createCellStyle();
        styleGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        styleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row rowhead = sheet.createRow((short) 0);
        rowhead.createCell(0).setCellValue("Symbol");
        rowhead.createCell(1).setCellValue("Open");
        rowhead.createCell(2).setCellValue("High");
        rowhead.createCell(3).setCellValue("Low");
        rowhead.createCell(4).setCellValue("LTP");
        rowhead.createCell(5).setCellValue("Position");

        setStyle(rowhead, styleHeader);

        for (int i = 0; i < data.length(); i++) {
            JSONObject d = data.getJSONObject(i);
            Row row = sheet.createRow((short) i + 1);

            String symbol = d.getString("symbol");
            Double open = Double.valueOf(d.getString("open").replaceAll(",", ""));
            Double high = Double.valueOf(d.getString("high").replaceAll(",", ""));
            Double low = Double.valueOf(d.getString("low").replaceAll(",", ""));
            Double ltP = Double.valueOf(d.getString("ltP").replaceAll(",", ""));

            row.createCell(0).setCellValue(symbol);
            row.createCell(1).setCellValue(open);
            row.createCell(2).setCellValue(high);
            row.createCell(3).setCellValue(low);
            row.createCell(4).setCellValue(ltP);

            if (isInteger(open) && (Double.compare(open, high) == 0)) {
                row.createCell(5).setCellValue("SELL");
                setStyle(row, styleRed);
            } else if (isInteger(open) && (Double.compare(open, low) == 0)) {
                row.createCell(5).setCellValue("BUY");
                setStyle(row, styleGreen);
            }
        }

        String fileName = obj.getString("time").substring(0, 12).replaceAll(",", "").replaceAll(" ", "_") + ".xlsx";
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        logger.info("Your excel file has been generated!");
        sendMail(fileName);
    }

    @GetMapping("/even_scheduler")
    public void evenScheduler() throws Exception {
        HttpClient client = HttpClients.custom().build();
        HttpUriRequest request = RequestBuilder.get()
                .setUri("https://www1.nseindia.com/live_market/dynaContent/live_watch/stock_watch/niftyStockWatch.json")
                .setHeader("X-Requested-With", "XMLHttpRequest")
                .setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                .build();
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        JSONObject obj = new JSONObject(responseString);
        JSONArray data = obj.getJSONArray("data");
        String fileName = obj.getString("time").substring(0, 12).replaceAll(",", "").replaceAll(" ", "_") + ".xlsx";

        FileInputStream inputDocument = new FileInputStream(new File(fileName));
        XSSFWorkbook workbook = new XSSFWorkbook(inputDocument);
        XSSFSheet sheet = workbook.getSheetAt(0);

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setItalic(true);

        XSSFCellStyle styleHeader = workbook.createCellStyle();
        styleHeader.setFont(font);

        XSSFCellStyle styleRed = workbook.createCellStyle();
        styleRed.setFillForegroundColor(IndexedColors.RED.getIndex());
        styleRed.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFCellStyle styleGreen = workbook.createCellStyle();
        styleGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        styleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        sheet.getRow(0).createCell(6).setCellValue("LTP (After Market)");
        sheet.getRow(0).getCell(6).setCellStyle(styleHeader);

        Map<String, Double> eveDataMap = new HashMap<>();
        for (int i = 0; i < data.length(); i++) {
            JSONObject d = data.getJSONObject(i);
            String symbol = d.getString("symbol");
            Double ltP = Double.valueOf(d.getString("ltP").replaceAll(",", ""));
            eveDataMap.put(symbol, ltP);
        }

        for (int i = 1; i <= 50; i++) {
            String sheetSymbol = sheet.getRow(i).getCell(0).getStringCellValue();
            // Double sheetOpen = Double.valueOf(sheet.getRow(i).getCell(1).getNumericCellValue());
            Double sheetLtP = Double.valueOf(sheet.getRow(i).getCell(4).getNumericCellValue());
            String sheetPosition = sheet.getRow(i).getCell(5) != null ?
                    sheet.getRow(i).getCell(5).getStringCellValue() : "";
            Double eveLtP = eveDataMap.get(sheetSymbol);

            sheet.getRow(i).createCell(6).setCellValue(eveLtP);
            if (sheetPosition.equals("SELL")) {
                if (Double.compare(sheetLtP, eveLtP) > 0) {
                    sheet.getRow(i).getCell(6).setCellStyle(styleRed);
                }
            } else if (sheetPosition.equals("BUY")) {
                if (Double.compare(sheetLtP, eveLtP) < 0) {
                    sheet.getRow(i).getCell(6).setCellStyle(styleGreen);
                }
            }
        }

        inputDocument.close();
        FileOutputStream outputFile = new FileOutputStream(new File(fileName));
        workbook.write(outputFile);
        outputFile.close();
        logger.info("Your excel file has been updated!");
        sendMail(fileName);
        new File(fileName).delete();
    }
}
