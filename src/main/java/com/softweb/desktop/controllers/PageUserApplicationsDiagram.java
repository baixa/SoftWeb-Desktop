package com.softweb.desktop.controllers;

import com.softweb.desktop.auth.Authorization;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.utils.print.OutputUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Класс-контроллер, содержащий страницу с диаграммами популярности приложений авторизованного пользователя
 */
public class PageUserApplicationsDiagram implements Initializable {

    /**
     * FXML узел, содержащий график популярности приложений
     */
    @FXML
    public BarChart<String, Integer> barChar;

    /**
     * Метод предназначен для инициализации контроллера.
     *
     * @param url URL-адрес, используемый для разрешения относительных путей для корневого объекта, или null, если местоположение неизвестно.
     * @param resourceBundle Пакет ресурсов, используемый для локализации корневого объекта, или null, если корневой объект не был локализован.
     */
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

    /**
     * Метод отправляет диаграмму на печать
     */
    public void printDiagram() {
        OutputUtils.printChart(barChar);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setHeaderText("Документ отправлен на печать!");
        alert.setTitle("Успешно!");
        alert.show();
    }

    /**
     * Метод сохраняет диаграмму как PDF документ
     */
    public void saveDiagramAsPDF() {
        try {
            String pathPDF = OutputUtils.saveChartAsPDF(barChar);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("Диаграмма сохранена в документе: " + pathPDF + "!");
            alert.setTitle("Успешно!");
            alert.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
