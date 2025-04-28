package com.example.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import org.json.JSONException;
//import org.json.JSONObject;
import java.io.IOException;
//import java.util.Objects;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;


public class DataFetcher {
    private static final String URL = "http://"; // Replace URL//
    private final OkHttpClient client = new OkHttpClient();

    public interface DataCallback {
        void onDataReceived(String angle1, String angle2);
        void onError(String error);
    }

    public void fetchData(DataCallback callback) {
        new Thread(() -> {
            try {
                Request request1 = new Request.Builder().url(URL).build();
                Request request2 = new Request.Builder().url(URL).build();
                try (Response response1 = client.newCall(request1).execute(); Response response2 = client.newCall(request2).execute()) {
                    if (response1.isSuccessful() && response1.body() != null && response2.isSuccessful() && response2.body() != null) {
                        String html1 = response1.body().string();
                        String html2 = response2.body().string();
                        callback.onDataReceived(html1, html2);

//                        Pattern pattern = Pattern.compile("\\d+(\\.\\d)?\\d*\\s");
//                        Matcher matcher = pattern.matcher(html1);
                        //regexp for value(?)

//                        if (matcher.find()) {
//                            float angle1 = Float.parseFloat(Objects.requireNonNull(matcher.group(1)));
//                            float angle2 = Float.parseFloat(Objects.requireNonNull(matcher.group(2)));
//                            callback.onDataReceived(angle1, angle2);
//                        } else {
//                            callback.onError("Failed to parse angle value1: " + response.message());
//                        }
                    } else
                        callback.onError("Request failed value1(if so): " + response1.message() + "Request failed value2(if so): "+ response2.message());
                }
            } catch (IOException e) {
                callback.onError("Network error: " + e.getMessage());
            }
        }).start();
    }
}