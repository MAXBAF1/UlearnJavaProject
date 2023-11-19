package org.example.db.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.example.db.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CSVParserService {

    private final StudentService studentService;

    @Autowired
    public CSVParserService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void parseAndSaveStudents(String filePath) {
        try (var reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(3).build()) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                var student = createStudentFromCSV(line);
                studentService.saveStudent(student);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private Student createStudentFromCSV(String[] csvData) {
        var dataParts = String.join(",", csvData).split(";", 8);
        var nameParts = dataParts[0].split(" ");
        var student = new Student();
        student.setUlearnId(UUID.randomUUID());
        student.setFirstName(csvData[0]);
        student.setLastName(csvData[1]);
        student.setMail(csvData[2]);

        return student;
    }
}