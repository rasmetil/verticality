package com.example.mesure_de_verticalite;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView angle_1;
    TextView angle_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // intialisation des textes pour les angles
        angle_1 = findViewById(R.id.angle_1);
        angle_2 = findViewById(R.id.angle_2);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
//    public void modify_angle_1(double angle) {
//        angle_1.setText(angle.toString());
//    }
//
//
}