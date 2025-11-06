package com.example.trabajito;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView nameTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        nameTV = findViewById(R.id.nameTV);

        String username = null;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("USERNAME");

        }

        if (username != null && !username.isEmpty()) {
            nameTV.setText("Hola, " + username + "!");
        } else {
            Log.e("MainActivity", "No se recibió el nombre de usuario.");
        }
    }
}
