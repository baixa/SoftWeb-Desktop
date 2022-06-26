package com.softweb.desktop;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Контрольный класс, являющийся начальной точкой запуска программы.
 */
@SpringBootApplication
public class DesktopApplication {
    public static void main(String[] args) {
        Application.launch(JavaFXMain.class, args);
    }
}
