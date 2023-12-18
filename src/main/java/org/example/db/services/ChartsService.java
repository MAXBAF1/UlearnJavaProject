package org.example.db.services;

import com.vk.api.sdk.objects.base.Sex;
import org.example.db.entities.Mark;
import org.example.db.entities.Student;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

@Service
public class ChartsService {
    private final StudentService studentService;

    @Autowired
    public ChartsService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void createCharts() {
        var students = studentService.getAllStudents();

        var exercisesWithScores = new LinkedHashMap<String, Integer>();
        var maleScore = 0.0;
        var maleCnt = 0;
        var femaleScore = 0.0;
        var femaleCnt = 0;
        for (var student : students) {
            List<Mark> marks = student.getMarks();
            for (var mark : marks) {
                if (mark.getScore() > 0 && mark.getExercise().getName().contains("ДЗ: Практика")) {
                    var exerciseName = mark.getExercise().getName();
                    if (!exercisesWithScores.containsKey(exerciseName))
                        exercisesWithScores.put(exerciseName, 1);
                    else {
                        var oldScore = exercisesWithScores.get(exerciseName);
                        exercisesWithScores.replace(exerciseName, oldScore + 1);
                    }
                }
            }

            if (student.getSex() == Sex.MALE) {
                maleCnt++;
                maleScore += student.getMarks().get(1).getScore() + student.getMarks().get(2).getScore();
            } else if (student.getSex() == Sex.FEMALE) {
                femaleCnt++;
                femaleScore += student.getMarks().get(1).getScore() + student.getMarks().get(2).getScore();
            }
        }
        var exerciseDataset = new DefaultCategoryDataset();
        for (var exerciseName: exercisesWithScores.keySet()) {
            exerciseDataset.setValue(exercisesWithScores.get(exerciseName), exerciseName, "");
        }
        var exerciseChart = ChartFactory.createBarChart("Практики", "Практики", "Баллы", exerciseDataset);
        exerciseChart.removeLegend();

        var dataset = new DefaultCategoryDataset();
        dataset.setValue(femaleScore / femaleCnt, "Ж", "Баллы");
        dataset.setValue(maleScore / maleCnt, "М", "Баллы");
        var chart = ChartFactory.createBarChart("Средний балл и пол", "", "Баллы", dataset);

        var sexCntDataset = new DefaultPieDataset();
        sexCntDataset.setValue("Женщины", femaleCnt);
        sexCntDataset.setValue("Мужчины", maleCnt);
        var sexCntChart = ChartFactory.createPieChart("Количество и пол", sexCntDataset);

        showChart(exerciseChart);
        showChart(sexCntChart);
        showChart(chart);
    }

    private void showChart(JFreeChart chart) {
        var chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 740));

        var frame = new JFrame(chart.getTitle().getText());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
