package com.example.trabajito.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajito.R;

public class FormServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.form_service_activity);

        int serviceNumber = getIntent().getIntExtra("service_number", -1);

        Toast.makeText(this, "serviceNumber: " + serviceNumber, Toast.LENGTH_SHORT).show();

    }
}
