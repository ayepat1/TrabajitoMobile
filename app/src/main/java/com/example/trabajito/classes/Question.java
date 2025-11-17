package com.example.trabajito.classes;

// Question.java
import java.util.List;

public class Question {
    public final String key;
    public final String label;
    public final String type; // "select", "number", "text", "textarea"
    public final List<QuestionOption> options; // null si no aplica

    public Question(String key, String label, String type, List<QuestionOption> options) {
        this.key = key;
        this.label = label;
        this.type = type;
        this.options = options;
    }
}
