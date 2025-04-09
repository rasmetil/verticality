package com.example.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mesure_de_verticalite.R;
import com.example.network.DataFetcher;

public class MainActivity extends AppCompatActivity {
    private TextView angle1TextView, angle2TextView;
    private final DataFetcher dataFetcher = new DataFetcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        angle1TextView = findViewById(R.id.angle_1);
        angle2TextView = findViewById(R.id.angle_2);

        fetchDataPeriodically();
    }

    private void fetchDataPeriodically() {
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dataFetcher.fetchData(new DataFetcher.DataCallback() {
                    @Override
                    public void onDataReceived(float angle1, float angle2) {
                        runOnUiThread(() -> {
                            angle1TextView.setText(String.format("Angle 1: %s", angle1));
                            angle2TextView.setText(String.format("Angle 2: %s", angle2));
                        });
                    }

                    @Override
                    public void onError(String error) {
                        runOnUiThread(() ->
                                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show()
                        );
                    }
                });

                handler.postDelayed(this, 2000); // 每 2 秒请求一次
            }
        };

        handler.post(runnable);
    }
}