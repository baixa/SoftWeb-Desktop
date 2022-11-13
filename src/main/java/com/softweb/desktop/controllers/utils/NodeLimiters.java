package com.softweb.desktop.controllers.utils;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * The class manages input restrictions for UI markup elements.
 */
public class NodeLimiters {

    /**
     * The method adds a length constraint for the text field
     *
     * @param textField Restricted field
     * @param maxLength Maximum length
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

    /**
     * The method adds a length constraint for the text field
     *
     * @param textArea Restricted field
     * @param maxLength Maximum length
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
