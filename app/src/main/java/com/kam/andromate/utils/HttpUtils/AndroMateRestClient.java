package com.kam.andromate.utils.HttpUtils;

import com.kam.andromate.IConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AndroMateRestClient {

    private final String baseUrl;
    private int timeout_ms = 5 * IConstants.SECONDS_TO_MS; // 10 seconds

    public AndroMateRestClient(String baseUrl, int timeout_ms) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        this.timeout_ms = timeout_ms;
    }

    public AndroMateRestClient(String baseUrl) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    public String get(String endpoint) throws Exception {
        URL url = new URL(baseUrl + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(timeout_ms);
        connection.setReadTimeout(timeout_ms);
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return readStream(connection);
        } else {
            throw new AndroMateHttpExceptions.INVALID_RESPONSE_CODE_EXCEPTION(HttpMethods.GET, responseCode);
        }
    }

    public String post(String jsonBody) throws Exception {
        URL url = new URL(baseUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(timeout_ms);
        connection.setReadTimeout(timeout_ms);
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            return readStream(connection);
        } else {
            throw new AndroMateHttpExceptions.INVALID_RESPONSE_CODE_EXCEPTION(HttpMethods.POST, responseCode);
        }
    }

    private String readStream(HttpURLConnection connection) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line.trim());
        }
        reader.close();
        return response.toString();
    }
}
