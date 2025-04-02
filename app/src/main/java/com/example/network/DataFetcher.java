package com.example.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

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
                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData);
                        float angle1 = (float) jsonObject.getDouble("angle_1");
                        float angle2 = (float) jsonObject.getDouble("angle_2");

                        callback.onDataReceived(angle1, angle2);
                    } else {
                        callback.onError("Request failed: " + response.message());
                    }
                }
            } catch (IOException e) {
                callback.onError("Network error: " + e.getMessage());
            } catch (JSONException e) {
                callback.onError("Bad data type: " + e.getMessage());
            }
        }).start();
    }
}