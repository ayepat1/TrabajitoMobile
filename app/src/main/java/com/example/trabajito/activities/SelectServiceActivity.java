package com.example.trabajito.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.trabajito.R;

public class SelectServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.select_service_activity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Array con los ids de los botones
        int[] buttonIds = {
                R.id.serviceButton1, R.id.serviceButton2, R.id.serviceButton3, R.id.serviceButton4,
                R.id.serviceButton5, R.id.serviceButton6, R.id.serviceButton7, R.id.serviceButton8,
                R.id.serviceButton9, R.id.serviceButton10, R.id.serviceButton11, R.id.serviceButton12,
                R.id.serviceButton13, R.id.serviceButton14, R.id.serviceButton15
        };

        // Asigna el mismo listener a todos
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(serviceClickListener);
        }
    }

    // Dentro de SelectServiceActivity.java

    private final View.OnClickListener serviceClickListener = v -> {
        String serviceName = ((android.widget.Button) v).getText().toString();
        Object tag = v.getTag();
        int serviceNumber = tag != null ? Integer.parseInt(tag.toString()) : -1;
        showCustomDialog(serviceName, serviceNumber);
    };

    private void showCustomDialog(String serviceName, int serviceNumber) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.confirm_requester, null);

        TextView serviceNameText = dialogView.findViewById(R.id.serviceName);
        serviceNameText.setText(serviceName);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        dialogView.findViewById(R.id.cancelButton).setOnClickListener(v -> dialog.dismiss());

        dialogView.findViewById(R.id.continueButton).setOnClickListener(v -> {
            // Lanzar la nueva actividad y pasar el número de servicio
            android.content.Intent intent = new android.content.Intent(this, FormServiceActivity.class);
            intent.putExtra("service_number", serviceNumber);
            startActivity(intent);
            dialog.dismiss();
        });

        dialog.show();
    }

}