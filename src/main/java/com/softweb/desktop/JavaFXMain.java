package com.softweb.desktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Контрольный класс, являющийся начальной точкой запуска JavaFX.
 */
public class JavaFXMain extends Application {

    /**
     * Контекст Spring
     */
    @lombok.Getter
    private static ConfigurableApplicationContext applicationContext;

    /**
     * Запуск JavaFX модуля
     * @param stage Окно программы
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
     * Завершение работы JavaFX модуля
     */
    @Override
    public void stop() {
        applicationContext.stop();
        Platform.exit();
    }

    /**
     * Класс-событие, запускающийся при открытии окна программы
     */
    static class StageReadyEvent extends ApplicationEvent {
        /**
         * Инициализирует объект
         * @param stage Окно программы
         */
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        /**
         * Получить окно программы
         * @return Окно программы
         */
        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
