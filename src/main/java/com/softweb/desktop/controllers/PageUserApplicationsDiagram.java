package com.softweb.desktop.controllers;

import com.softweb.desktop.auth.Authorization;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.utils.print.OutputUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class PageUserApplicationsDiagram implements Initializable {

    @FXML
    public BarChart<String, Integer> barChar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Integer> downloadsSeries = new XYChart.Series<>();
        downloadsSeries.setName("Загрузки");
        XYChart.Series<String, Integer> viewsSeries = new XYChart.Series<>();
        viewsSeries.setName("Просмотры");
        Authorization.getCurrentUser().getApplications().forEach(item -> {
            downloadsSeries.getData().add(new XYChart.Data<>(item.getName(), 100));
            viewsSeries.getData().add(new XYChart.Data<>(item.getName(), 100));
        });
        barChar.getData().add(downloadsSeries);
        barChar.getData().add(viewsSeries);

        Timeline tl = new Timeline();
        tl.getKeyFrames().add(
                new KeyFrame(Duration.millis(700),
                        actionEvent -> {
                            for (XYChart.Series<String, Integer> series : barChar.getData()) {
                                for (XYChart.Data<String, Integer> data : series.getData()) {
                                    Application application = Authorization.getCurrentUser().getApplications().stream().filter(item -> item.getName().equals(data.getXValue())).findFirst().orElse(null);
                                    if(application == null)
                                        return;

                                    if (series.getName().equals("Загрузки"))
                                        data.setYValue(application.getDownloads());
                                    else
                                        data.setYValue(application.getViews());
                                }
                            }
                        }
                ));
        tl.setCycleCount(1);
        tl.play();
    }

    public void printDiagram() {
        OutputUtils.printChart(barChar);
    }

    public void saveDiagram() {
        OutputUtils.saveChartAsPDF(barChar);
    }
}
