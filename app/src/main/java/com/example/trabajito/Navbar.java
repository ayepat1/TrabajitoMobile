package com.example.trabajito;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajito.R;


public class Navbar extends AppCompatActivity {
    private Context context;

    private TextView navTitle;
    private Button btnGoToRegister;

    public Navbar(Context context) {
        this.context = context;
    }

    public Navbar(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nav_bar);

        navTitle = findViewById(R.id.nav_title);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);

        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Navbar.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
