package com.example.trabajito.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.PasswordTransformationMethod;
import android.text.method.HideReturnsTransformationMethod;
import android.widget.ImageView;
import android.app.DatePickerDialog;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajito.ApiClient;
import com.example.trabajito.ApiService;
import com.example.trabajito.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etDni, etPassword, etPassword2,
            etAddress, etAddressNumber, etDepartment, etPostalCode, etEmail, etEmail2,
            etPhoneNumber, etDateOfBirth;
    private Spinner spinnerIdType;
    private Button btnRegister, btnAccept;
    private TextView textGoToLogin;
    private ImageView ivPasswordToggle, ivPasswordToggle2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        bindViews();
        setupSpinner();
        setupPasswordToggles();


        btnRegister.setOnClickListener(v -> attemptRegistration());

        textGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        etDateOfBirth.setOnClickListener(v -> showDatePickerDialog());
    }

    private void bindViews() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etDni = findViewById(R.id.etDni);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        ivPasswordToggle = findViewById(R.id.ivPasswordToggle);
        ivPasswordToggle2 = findViewById(R.id.ivPasswordToggle2);
        etAddress = findViewById(R.id.etAddress);
        etAddressNumber = findViewById(R.id.addressHeight);
        etDepartment = findViewById(R.id.dpto);
        etPostalCode = findViewById(R.id.cp);
        etEmail = findViewById(R.id.email);
        etEmail2 = findViewById(R.id.email2);
        etPhoneNumber = findViewById(R.id.phoneNumber);
        etDateOfBirth = findViewById(R.id.dateOfBirth);
        spinnerIdType = findViewById(R.id.spinner);
        btnRegister = findViewById(R.id.btnRegister);
        textGoToLogin = findViewById(R.id.text_goToLogin);
    }

    private void setupPasswordToggles() {
        ivPasswordToggle.setOnClickListener(v -> {
            if (etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivPasswordToggle.setImageResource(R.drawable.ic_eye_open);
            } else {
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivPasswordToggle.setImageResource(R.drawable.ic_eye_closed);
            }
            etPassword.setSelection(etPassword.getText().length());
        });

        ivPasswordToggle2.setOnClickListener(v -> {
            if (etPassword2.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                etPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivPasswordToggle2.setImageResource(R.drawable.ic_eye_open);
            } else {
                etPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivPasswordToggle2.setImageResource(R.drawable.ic_eye_closed);
            }
            etPassword2.setSelection(etPassword2.getText().length());
        });
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.id_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdType.setAdapter(adapter);
    }

    private void attemptRegistration() {
        if (!validateInput()) {
            Toast.makeText(this, "Por favor, corrige los errores marcados", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> fields = new HashMap<>();
        fields.put("firstName", etFirstName.getText().toString().trim());
        fields.put("lastName", etLastName.getText().toString().trim());
        fields.put("dni", etDni.getText().toString().trim());
        fields.put("email", etEmail.getText().toString().trim());
        fields.put("birthDate", etDateOfBirth.getText().toString().trim()); // Formato YYYY-MM-DD
        fields.put("password", etPassword.getText().toString().trim());
        fields.put("phone", etPhoneNumber.getText().toString().trim());
        fields.put("address", etAddress.getText().toString().trim());
        fields.put("number", etAddressNumber.getText().toString().trim());
        fields.put("departmentNumber", etDepartment.getText().toString().trim());
        fields.put("postalCode", etPostalCode.getText().toString().trim());
        fields.put("idType", spinnerIdType.getSelectedItem().toString()); // Obtener el valor del Spinner

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Void> call = apiService.signup(fields);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showSuccessDialog();
                } else {
                    String errorMessage = "Error en el registro.";
                    if (response.errorBody() != null) {
                        try {
                            JSONObject errorObject = new JSONObject(response.errorBody().string());
                            errorMessage = errorObject.getString("message");
                        } catch (IOException | JSONException e) {
                            Log.e("API_SIGNUP_ERROR", "Error al parsear el error del JSON", e);
                        }
                    }
                    Toast.makeText(RegisterActivity.this, errorMessage + " (Código: " + response.code() + ")", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_SIGNUP_FAILURE", "Fallo de conexión: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validateInput() {
        // Validación de contraseñas
        String password = etPassword.getText().toString().trim();
        String password2 = etPassword2.getText().toString().trim();
        if (password.isEmpty() || password.length() < 6) { // Ejemplo: mínimo 6 caracteres
            etPassword.setError("La contraseña debe tener al menos 6 caracteres");
            return false;
        }
        if (!password.equals(password2)) {
            etPassword2.setError("Las contraseñas no coinciden");
            return false;
        }

        // Validación de emails
        String email = etEmail.getText().toString().trim();
        String email2 = etEmail2.getText().toString().trim();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Ingresa un correo válido");
            return false;
        }
        if (!email.equals(email2)) {
            etEmail2.setError("Los correos electrónicos no coinciden");
            return false;
        }

        if (etFirstName.getText().toString().trim().isEmpty()) {
            etFirstName.setError("El nombre es obligatorio");
            return false;
        }
        if (etLastName.getText().toString().trim().isEmpty()) {
            etLastName.setError("El apellido es obligatorio");
            return false;
        }
        if (etDni.getText().toString().trim().isEmpty()) {
            etDni.setError("El número de identificación es obligatorio");
            return false;
        }

        return true;
    }

    //popup
    @SuppressLint("MissingInflatedId")
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_registration_success, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnAccept = dialogView.findViewById(R.id.btn_dialog_accept);
        btnAccept.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        dialog.show();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    int monthForDisplay = selectedMonth + 1;

                    String formattedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", selectedYear, monthForDisplay, selectedDay);

                    etDateOfBirth.setText(formattedDate);
                },
                year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }
}