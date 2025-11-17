package com.example.trabajito.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajito.R;

public class MainActivity extends AppCompatActivity {

    private TextView nameTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        nameTV = findViewById(R.id.nameTV);

        String username = null;
        String token = null;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            token = bundle.getString("TOKEN");

        }

        if (username != null && !username.isEmpty()) {
            nameTV.setText("Hola, " + token + "!");
        } else {
            Log.e("MainActivity", "No se recibió el nombre de usuario.");
        }
    }
}
