package com.example.mesure_de_verticalite;
//
//import android.os.Bundle;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class MainActivity extends AppCompatActivity {
//    TextView angle_1;
//    TextView angle_2;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        // intialisation des textes pour les angles
//        angle_1 = findViewById(R.id.angle_1);
//        angle_2 = findViewById(R.id.angle_2);
//
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
////    public void modify_angle_1(double angle) {
////        angle_1.setText(angle.toString());
////    }
////
////
//}

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.example.network.DataFetcher;
import com.example.network.DataFetcher.DataCallback;
import androidx.appcompat.app.AppCompatActivity;
//import com.example.mesure_de_verticalite.R;


public class MainActivity extends AppCompatActivity {

    private TextView angle1TextView;
    private TextView angle2TextView;
    private Handler handler = new Handler();
    private DataFetcher dataFetcher = new DataFetcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des TextView
        angle1TextView = findViewById(R.id.angle_1);
        angle2TextView = findViewById(R.id.angle_2);

        // Démarrer la mise à jour continue des angles
        startUpdatingAngles();
    }

    private void startUpdatingAngles() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getAngles();

                // Relancer toutes les 1 seconde
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void getAngles() {
        // Appeler fetchData pour récupérer les données
        dataFetcher.fetchData(new DataCallback() {
            @Override
            public void onDataReceived(String angle1, String angle2) {
                // Mise à jour des TextView avec les nouveaux angles
                angle1TextView.setText( angle1);
                angle2TextView.setText( angle2);
            }

            @Override
            public void onError(String error) {
                // Gestion des erreurs et affichage dans les TextViews
                angle1TextView.setText("Erreur : " + error);
                angle2TextView.setText("");
            }
        });
    }
}
