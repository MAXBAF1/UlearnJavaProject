package org.example.controllers;

import org.example.db.services.CSVParserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class StudentController {
    private final CSVParserService csvParserService;

    public StudentController(CSVParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    @PostMapping("parse")
    public ResponseEntity<String> parseCSV(@RequestParam String filePath) {
        csvParserService.parseAndSaveStudents(filePath);
        return ResponseEntity.ok("Data parsed and saved successfully.");
    }
}