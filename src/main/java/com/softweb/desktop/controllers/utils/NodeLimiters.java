package com.softweb.desktop.controllers.utils;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Класс управляет ограничениями на ввод для элементов UI разметки
 */
public class NodeLimiters {

    /**
     * Метод добавляет ограничение длины для текстового поля
     * @param textField Ограничеваемое поле
     * @param maxLength Максимальная длина
     */
    public static void addTextLimiter(final TextField textField, final int maxLength) {
        if(textField.getText() == null)
            return;

        textField.textProperty().addListener((ov, oldValue, newValue) -> {
            if(textField.getText() == null)
                return;

            if (textField.getText().length() > maxLength) {
                String s = textField.getText().substring(0, maxLength);
                textField.setText(s);
            }
        });
    }

    /** Метод добавляет ограничение длины для текстового поля
     * @param textArea Ограничеваемое поле
     * @param maxLength Максимальная длина
     */
    public static void addTextLimiter(final TextArea textArea, final int maxLength) {

        textArea.textProperty().addListener((ov, oldValue, newValue) -> {
            if(textArea.getText() == null)
                return;

            if (textArea.getText().length() > maxLength) {
                String s = textArea.getText().substring(0, maxLength);
                textArea.setText(s);
            }
        });
    }
}
