package com.company.view;

import javax.swing.*;
import java.awt.*;

/**
 * The box that shows the chat
 */
public class ChatBox extends JTextPane {

    /**
     * The constructor for the chat box
     */
    public ChatBox() {
        super.setEditable(false);
    }

    /**
     * Updates the chat box with the given message
     * @param message The given message to print to the screen
     * @param messageColor The colour of the given message
     */
    public void printMessage(String message, Color messageColor) {
        // TODO: Figure out how to set colors for text
        this.appendLine(message);
    }

    /**
     * Updates the chat box with the given message, along with who said it and the colours of the name and message
     * @param name The name of the person saying the message
     * @param nameColor The colour of the name
     * @param message The message that the person sent
     * @param messageColor The colour of the message
     */
    public void printMessage(String name, Color nameColor, String message, Color messageColor) {
        // TODO: Figure out how to set colors for text
        this.appendLine(name + ": " + message);
    }

    /**
     * Appends a line to the chat box, separating it from existing text with a newline
     * @param line The line to append
     */
    private void appendLine(String line) {
        if (super.getText().isEmpty()) super.setText(line);
        else super.setText(super.getText() + '\n' + line);
    }
}
