package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

// Класс для работы с базой данных SQLite
class SQLiteDB {
    private static final String URL = "jdbc:sqlite:your_database.db";

    // Метод для создания таблиц в БД согласно набору объектов
    public static void createTables() {
        try (Connection connection = DriverManager.getConnection(URL)) {
            // Создание таблиц в БД
            // ...
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для сохранения данных в БД
    public static void saveData(List<Student> data) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            for (var item : data) {
                // Вставка данных в таблицу
                // ...
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для выполнения SQL-запросов к БД
    public static void executeQuery(String query) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            // Выполнение SQL-запроса
            // ...
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
