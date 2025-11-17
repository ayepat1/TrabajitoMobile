package com.example.trabajito.activities;

import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.*;
import com.example.trabajito.classes.*;
import java.util.Arrays;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trabajito.R;

public class FormServiceActivity extends AppCompatActivity {
    private static final List<String> PREGUNTAS_FIJAS = Arrays.asList(
            "titulo", "urgencia", "fecha", "descripcion"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_service_activity);

        LinearLayout fieldsContainer = findViewById(R.id.fieldsContainer);
        TextView tituloServicio = findViewById(R.id.tituloServicio);

        // Obtener el id del servicio desde el intent (por defecto 1)
        int serviceId = getIntent().getIntExtra("service_id", 1);
        ServiceType selectedService = null;
        for (ServiceType s : QuestionsHelper.SERVICE_TYPES) {
            if (s.id == serviceId) {
                selectedService = s;
                break;
            }
        }

        if (selectedService != null) {
            tituloServicio.setText("Formulario para: " + selectedService.label);

            for (Question q : selectedService.questions) {
                if (PREGUNTAS_FIJAS.contains(q.key)) continue;

                // Label
                TextView label = new TextView(this);
                label.setText(q.label);
                label.setTextColor(getResources().getColor(R.color.black));
                label.setTextSize(16);
                label.setTypeface(null, android.graphics.Typeface.BOLD);
                LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                labelParams.setMargins(0, 16, 0, 4);
                label.setLayoutParams(labelParams);
                fieldsContainer.addView(label);

                switch (q.type) {
                    case "select":
                        Spinner spinner = new Spinner(this, Spinner.MODE_DROPDOWN);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_spinner_item,
                                getOptionLabels(q.options)
                        );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        spinner.setBackgroundResource(R.drawable.edit_textbg);
                        LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        spinnerParams.setMargins(0, 0, 0, 16);
                        spinner.setLayoutParams(spinnerParams);
                        fieldsContainer.addView(spinner);
                        break;
                    case "number":
                        EditText numberInput = new EditText(this);
                        numberInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                        numberInput.setBackgroundResource(R.drawable.edit_textbg);
                        numberInput.setTextColor(getResources().getColor(R.color.black));
                        numberInput.setHint("Ingrese un número");
                        LinearLayout.LayoutParams numberParams = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        numberParams.setMargins(0, 0, 0, 16);
                        numberInput.setLayoutParams(numberParams);
                        fieldsContainer.addView(numberInput);
                        break;
                    case "text":
                        EditText textInput = new EditText(this);
                        textInput.setInputType(InputType.TYPE_CLASS_TEXT);
                        textInput.setBackgroundResource(R.drawable.edit_textbg);
                        textInput.setTextColor(getResources().getColor(R.color.black));
                        textInput.setHint("Ingrese texto");
                        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        textParams.setMargins(0, 0, 0, 16);
                        textInput.setLayoutParams(textParams);
                        fieldsContainer.addView(textInput);
                        break;
                    case "textarea":
                        EditText textArea = new EditText(this);
                        textArea.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                        textArea.setMinLines(3);
                        textArea.setBackgroundResource(R.drawable.edit_textbg);
                        textArea.setTextColor(getResources().getColor(R.color.black));
                        textArea.setHint("Ingrese detalles adicionales");
                        LinearLayout.LayoutParams areaParams = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, 200);
                        areaParams.setMargins(0, 0, 0, 16);
                        textArea.setLayoutParams(areaParams);
                        fieldsContainer.addView(textArea);
                        break;
                }
            }
        }
    }

    private List<String> getOptionLabels(List<QuestionOption> options) {
        if (options == null) return Arrays.asList();
        List<String> labels = new java.util.ArrayList<>();
        for (QuestionOption opt : options) {
            labels.add(opt.label);
        }
        return labels;
    }
}