package org.example.db.services;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.example.db.entities.Exercise;
import org.example.db.entities.Mark;
import org.example.db.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class CSVParserService {
    private Exercise[] exercises;
    private final StudentService studentService;

    @Autowired
    public CSVParserService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void parseAndSaveStudents(String filePath) {
        try (var fileInputStream = new FileInputStream(filePath);
             var inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("windows-1251"));
             var reader = new CSVReaderBuilder(inputStreamReader).build()) {
            var titles = String.join(",", reader.readNext()).split(";");
            exercises = Arrays.stream(titles).map(s -> {
                var exercise = new Exercise();
                exercise.setName(s);
                return exercise;
            }).toArray(Exercise[]::new);
            reader.skip(1);
            String[] line;
            var cnt = 0;
            while ((line = reader.readNext()) != null) {
                var student = createStudentFromCSV(line);
                studentService.saveStudent(student);
                System.out.println(++cnt);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private Student createStudentFromCSV(String[] csvData) {
        var dataParts = String.join(",", csvData).split(";");
        var nameParts = dataParts[0].split("\\s+");

        var student = new Student();
        student.setFirstName(nameParts.length > 1 ? nameParts[1] : nameParts[0]);
        student.setLastName(nameParts.length == 1 ? "" : nameParts[0]);
        student.setId(UUID.fromString(dataParts[1]));
        student.setMail(dataParts[2]);
        student.setMarks(getMarks(dataParts));

        return student;
    }

    private List<Mark> getMarks(String[] dataParts) {
        var marks = new ArrayList<Mark>();

        for (var i = 4; i < dataParts.length; i++) {
            var mark = new Mark();

            mark.setExercise(exercises[i]);
            mark.setScore(Integer.valueOf(dataParts[i]));
            marks.add(mark);
        }

        return marks;
    }
}