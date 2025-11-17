package com.example.trabajito.classes;

import java.util.Arrays;
import java.util.List;

public class QuestionsHelper {
    public static final List<Question> AIRES_ACONDICIONADOS_QUESTIONS = Arrays.asList(
            new Question(
                    "tipo_servicio",
                    "¿Qué tipo de servicio necesitas?",
                    "select",
                    Arrays.asList(
                            new QuestionOption("instalacion", "Instalación"),
                            new QuestionOption("mantenimiento", "Mantenimiento"),
                            new QuestionOption("reparacion", "Reparación"),
                            new QuestionOption("retiro", "Retiro")
                    )
            ),
            new Question(
                    "cantidad_equipos",
                    "¿Cuántos equipos de aire acondicionado son?",
                    "number",
                    null
            ),
            new Question(
                    "tipo_equipo",
                    "¿Qué tipo de equipo es?",
                    "select",
                    Arrays.asList(
                            new QuestionOption("split", "Split"),
                            new QuestionOption("ventana", "Ventana"),
                            new QuestionOption("central", "Central"),
                            new QuestionOption("otro", "Otro")
                    )
            ),
            new Question(
                    "capacidad",
                    "¿Cuál es la capacidad (en frigorías o BTU) de los equipos?",
                    "text",
                    null
            ),
            new Question(
                    "acceso",
                    "¿El lugar de instalación/reparación es de fácil acceso?",
                    "select",
                    Arrays.asList(
                            new QuestionOption("si", "Sí"),
                            new QuestionOption("no", "No")
                    )
            ),
            new Question(
                    "requiere_gas",
                    "¿Se requiere recarga de gas refrigerante?",
                    "select",
                    Arrays.asList(
                            new QuestionOption("si", "Sí"),
                            new QuestionOption("no", "No")
                    )
            ),
            new Question(
                    "descripcion_adicional",
                    "¿Hay algo más que debamos saber?",
                    "textarea",
                    null
            )
    );

    public static final List<ServiceType> SERVICE_TYPES = Arrays.asList(
            new ServiceType(
                    1,
                    "Aire acondicionado",
                    AIRES_ACONDICIONADOS_QUESTIONS
            )
            // Puedes agregar más servicios aquí
    );
}