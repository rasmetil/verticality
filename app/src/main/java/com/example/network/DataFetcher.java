package com.example.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import org.json.JSONException;
//import org.json.JSONObject;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataFetcher {
    private static final String URL = "http://"; // Replace URL//
    private final OkHttpClient client = new OkHttpClient();

    public interface DataCallback {
        void onDataReceived(float angle1, float angle2);
        void onError(String error);
    }

    public void fetchData(DataCallback callback) {
        new Thread(() -> {
            try {
                Request request = new Request.Builder().url(URL).build();
                try (Response response = client.newCall(request).execute()) {

                    if (response.isSuccessful() && response.body() != null) {
                        String html = response.body().string();

                        // 正则提取角度值
                        Pattern pattern = Pattern.compile("\\d+(\\.\\d)?\\d*\\s");
                        Matcher matcher = pattern.matcher(html);

                        if (matcher.find()) {
                            float angle1 = Float.parseFloat(Objects.requireNonNull(matcher.group(1)));
                            float angle2 = Float.parseFloat(Objects.requireNonNull(matcher.group(2)));
                            callback.onDataReceived(angle1, angle2);
                        } else {
                            callback.onError("Failed to parse angle values: " + response.message());
                        }
                    } else {
                        callback.onError("Request failed: " + response.message());
                    }
                }
            } catch (IOException e) {
                callback.onError("Network error: " + e.getMessage());
            }
        }).start();
    }
}