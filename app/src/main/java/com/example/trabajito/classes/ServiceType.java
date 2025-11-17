package com.example.trabajito.classes;

import java.util.List;

public class ServiceType {
    public final int id;
    public final String label;
    public final List<Question> questions;

    public ServiceType(int id, String label, List<Question> questions) {
        this.id = id;
        this.label = label;
        this.questions = questions;
    }
}