package com.softweb.desktop;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The control class, which is the starting point for starting the program.
 */
@SpringBootApplication
public class DesktopApplication {
    public static void main(String[] args) {
        Application.launch(JavaFXMain.class, args);
    }
}
