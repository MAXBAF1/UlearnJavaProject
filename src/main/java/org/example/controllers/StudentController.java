package org.example.controllers;

import org.example.db.services.CSVParserService;
import org.example.db.services.VKApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class StudentController {
    private final CSVParserService csvParserService;
    private final VKApiService vkApiService;

    public StudentController(CSVParserService csvParserService, VKApiService vkApiService) {
        this.csvParserService = csvParserService;
        this.vkApiService = vkApiService;
    }

    @PostMapping("parse")
    public ResponseEntity<String> parseCSV(@RequestParam String filePath) {
        csvParserService.parseAndSaveStudents(filePath);
        return ResponseEntity.ok("Data parsed and saved successfully.");
    }

    @PostMapping("vkData")
    public ResponseEntity<String> parseVkData(
            @RequestParam Integer appId,
            @RequestParam String accessToken,
            @RequestParam String clientSecret,
            @RequestParam String groupId
    ) {
        vkApiService.getAdditionalInfoAndSaveStudents(appId, accessToken, clientSecret, groupId);
        return ResponseEntity.ok("Data parsed and saved successfully.");
    }
}