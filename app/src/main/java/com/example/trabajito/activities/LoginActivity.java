package com.example.trabajito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import com.example.trabajito.ApiClient;
import com.example.trabajito.ApiService;
import com.example.trabajito.LoginResponse;
import com.example.trabajito.NavbarManager;
import com.example.trabajito.R;
import com.example.trabajito.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView textForgotPassword, textCreateAccount, tvLoginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.btnLogin);
        //textForgotPassword = findViewById(R.id.text_forgotpassword);
        textCreateAccount = findViewById(R.id.text_createaccount);
        //btnNavRegister = findViewById(R.id.btnNavRegister);
        tvLoginError = findViewById(R.id.tv_login_error);

        NavbarManager.setupNavbar(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                loginUser(email, password);
            }
        });

        textCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loginUser(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        tvLoginError.setVisibility(View.GONE);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LoginResponse> call = apiService.login(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    getSharedPreferences("auth", MODE_PRIVATE)
                            .edit()
                            .putString("jwt_token", token)
                            .apply();

                    User user = response.body().getUser();
                    if (user != null) {
                        tvLoginError.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Bienvenido " + user.getFirstName(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("TOKEN", token);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    handleApiError(response);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("API_LOGIN", "Fallo en la conexión: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void handleApiError(Response<LoginResponse> response) {
        String errorMessage = "Error desconocido. Inténtalo más tarde."; // Mensaje por defecto

        if (response.errorBody() != null) {
            try {
                String errorBodyString = response.errorBody().string();
                JSONObject errorObject = new JSONObject(errorBodyString);
                errorMessage = errorObject.getString("message");
            } catch (IOException | JSONException e) {
                Log.e("API_LOGIN_ERROR", "Error al parsear el JSON de error", e);
            }
        }

        int statusCode = response.code();

        if (statusCode == 403 && errorMessage.contains("Debes confirmar tu correo")) {
            tvLoginError.setText(errorMessage);
            tvLoginError.setVisibility(View.VISIBLE);
        } else if (statusCode == 400 && (errorMessage.contains("Incorrectos") || errorMessage.contains("no encontrado"))) {
            tvLoginError.setText("Correo Electrónico o Contraseña Incorrectos");
            tvLoginError.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(LoginActivity.this, "Ocurrió un error. Código: " + statusCode, Toast.LENGTH_SHORT).show();
            Log.e("API_LOGIN_UNHANDLED", "Código: " + statusCode + ", Mensaje: " + errorMessage);
        }
    }

}