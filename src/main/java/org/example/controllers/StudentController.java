package org.example.controllers;

import org.example.db.services.CSVParserService;
import org.example.db.services.ChartsService;
import org.example.db.services.VKApiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class StudentController {
    private final CSVParserService csvParserService;
    private final VKApiService vkApiService;
    private final ChartsService chartsService;

    public StudentController(CSVParserService csvParserService, VKApiService vkApiService, ChartsService chartsService) {
        this.csvParserService = csvParserService;
        this.vkApiService = vkApiService;
        this.chartsService = chartsService;
    }

    @PostMapping("parse")
    public void parseCSV(@RequestParam String filePath) {
        csvParserService.parseAndSaveStudents(filePath);
    }

    @PostMapping("vkData")
    public void parseVkData(
            @RequestParam Integer appId,
            @RequestParam String accessToken,
            @RequestParam String clientSecret,
            @RequestParam String groupId
    ) {
        vkApiService.getAdditionalInfoAndSaveStudents(appId, accessToken, clientSecret, groupId);
    }

    @PostMapping("charts")
    public void createCharts() {
        chartsService.createCharts();
    }
}