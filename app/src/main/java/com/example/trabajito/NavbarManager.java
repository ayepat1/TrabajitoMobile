// Nuevo archivo: NavbarManager.java
package com.example.trabajito;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class NavbarManager {

    public static void setupNavbar(Activity activity) {
        // Busca los botones DENTRO de la actividad que se le pasa
        Button btnNavRegister = activity.findViewById(R.id.btnGoToRegister); // Asume este ID
       // Button btnNavLogin = activity.findViewById(R.id.btnNavLogin); // Asume este ID

        if (btnNavRegister != null) {
            btnNavRegister.setOnClickListener(v -> {
                Intent intent = new Intent(activity, RegisterActivity.class);
                activity.startActivity(intent);
            });
        }

//        if (btnNavLogin != null) {
//            btnNavLogin.setOnClickListener(v -> {
//                Intent intent = new Intent(activity, LoginActivity.class);
//                activity.startActivity(intent);
//            });
//        }
        // ... puedes añadir más botones aquí (Home, Profile, etc.)
    }
}
