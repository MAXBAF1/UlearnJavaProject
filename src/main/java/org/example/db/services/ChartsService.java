package org.example.db.services;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

@Service
public class ChartsService {
    private final StudentService studentService;

    @Autowired
    public ChartsService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void createCharts() {
        var students = studentService.getAllStudents();

        showLineChart();
        for (var student : students) {

        }
    }

    private void showLineChart() {
        var series = new XYSeries("Chart");

        // Add data points to the series (replace these with your actual data)
        series.add(1, 23);
        series.add(2, 14);
        series.add(3, 15);
        series.add(4, 24);
        series.add(5, 34);

        var dataset = new XYSeriesCollection(series);

        var chart = ChartFactory.createXYLineChart(
                "Student Performance", // Chart title
                "X Axis Label",       // X-axis label
                "Y Axis Label",       // Y-axis label
                dataset              // Dataset
        );

        var plot = chart.getXYPlot();
        var xAxis = plot.getDomainAxis();

        var chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));

        var frame = new JFrame("'s Performance");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
