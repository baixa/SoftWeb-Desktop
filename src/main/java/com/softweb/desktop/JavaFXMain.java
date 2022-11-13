package com.softweb.desktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * The control class that is the starting point for starting JavaFX.
 */
public class JavaFXMain extends Application {

    /**
     * Spring context
     */
    @lombok.Getter
    private static ConfigurableApplicationContext applicationContext;

    /**
     * Запуск JavaFX модуля
     *      * @param stage Окно программы
     */
    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    /**
     * Инициализация объекта и сохранение контекста программы
     */
    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(DesktopApplication.class).run();
    }

    /**
     * Shutting down a JavaFX module
     */
    @Override
    public void stop() {
        applicationContext.stop();
        Platform.exit();
    }

    /**
     * Event class that fires when the program window is opened
     */
    static class StageReadyEvent extends ApplicationEvent {
        /**
         * Initializes an object
         *
         * @param stage Program window
         */
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        /**
         * Get program window
         *
         * @return Program window
         */
        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
