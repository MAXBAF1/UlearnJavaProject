package org.example;

class Student {
    private String name;
    private String ulearnId;
    private String email;
    private String group;
    private int act;
    private int exercises;
    private int homework;

    Student(String name, String ulearnId, String email, String group, int act, int exercises, int homework) {
        this.name = name;
        this.ulearnId = ulearnId;
        this.email = email;
        this.group = group;
        this.act = act;
        this.exercises = exercises;
        this.homework = homework;
    }

    // Конструктор, геттеры и сеттеры
}