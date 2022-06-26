package com.softweb.desktop.controllers.utils;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса NodeLimiters
 */
class NodeLimitersTest {

    /**
     * Преднастройка окружения
     */
    @BeforeEach
    void setUp() {
        JFXPanel fxPanel = new JFXPanel();
    }

    /**
     * Тест на обрезание превышающего заданный ограничитель текста
     */
    @Test
    void addTextFieldLimiterPositive() {
        TextField textField = new TextField();
        NodeLimiters.addTextLimiter(textField, 10);
        textField.setText("Некий текст, длина которого превышает 10 символов");
        assertEquals(textField.getText(), "Некий текс");
    }

    /**
     * Тест на сохранение непревышающего заданный ограничитель текста
     */
    @Test
    void addTextFieldLimiterNegative() {
        TextField textField = new TextField();
        NodeLimiters.addTextLimiter(textField, 100);
        textField.setText("Некий текст, длина которого меньше 100 символов");
        assertEquals(textField.getText(), "Некий текст, длина которого меньше 100 символов");
    }

    /**
     * Тест на обрезание превышающего заданный ограничитель текста
     */
    @Test
    void addTextAreaLimiterPositive() {
        TextArea textArea = new TextArea();
        NodeLimiters.addTextLimiter(textArea, 10);
        textArea.setText("Некий текст, длина которого превышает 10 символов");
        assertEquals(textArea.getText(), "Некий текс");
    }

    /**
     * Тест на сохранение непревышающего заданный ограничитель текста
     */
    @Test
    void addTextAreaLimiterNegative() {
        TextArea textArea = new TextArea();
        NodeLimiters.addTextLimiter(textArea, 100);
        textArea.setText("Некий текст, длина которого меньше 100 символов");
        assertEquals(textArea.getText(), "Некий текст, длина которого меньше 100 символов");
    }
}