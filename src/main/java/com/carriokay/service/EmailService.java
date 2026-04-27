package com.carriokay.service;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

@Service
public class EmailService {

    public void sendSimpleMail(String to, String subject, String text) {

        try {
            URL url = new URL("https://api.resend.com/emails");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + System.getenv("RESEND_API_KEY"));
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String body = "{"
                    + "\"from\":\"onboarding@resend.dev\","
                    + "\"to\":\"" + to + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"html\":\"<p>" + text + "</p>\""
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            System.out.println("Email response code: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}