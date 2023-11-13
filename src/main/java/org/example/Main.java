package org.example;

import com.opencsv.CSVParser;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Чтение данных из CSV файла и создание объектов YourDataClass
        var dataList = readDataFromCSV("basicprogramming.csv"); 

        // Создание таблиц в БД
        SQLiteDB.createTables();

        // Сохранение данных в БД
        SQLiteDB.saveData(dataList);

        // Выполнение SQL-запросов к БД
        SQLiteDB.executeQuery("SELECT * FROM your_table"); // Замените на свой запрос

        // Визуализация числовых данных в виде диаграмм
        // ...

        // Вывод данных в консоль
        // ...
    }

    private static List<Student> readDataFromCSV(String filePath) {
        List<Student> studentList = new ArrayList<>();

        try (var reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(3).build()) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                var dataParts = String.join(",", line).split(";", 8);

                var student = new Student(dataParts[0], dataParts[1], dataParts[2], dataParts[3], Integer.parseInt(dataParts[4]), Integer.parseInt(dataParts[5]), Integer.parseInt(dataParts[6]));

                studentList.add(student);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return studentList;
    }
}
