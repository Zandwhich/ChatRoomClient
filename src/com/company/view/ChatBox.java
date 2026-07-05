package com.company.view;

import com.company.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * The box that shows the chat
 */
public class ChatBox extends JTextPane {

    /**
     * The controller
     */
    private Controller controller;

    /**
     * The constructor for the chat box
     * @param controller The controller
     */
    public ChatBox(Controller controller) {
        this.controller = controller;

        super.setEditable(false);
    }

    /**
     * Updates the chat box with the given message
     * @param message The given message to print to the screen
     * @param messageColor The color of the given message
     */
    public void printMessage(String message, Color messageColor) {
        // TODO: Figure out how to set colors for text
        if (super.getText().equals("")) super.setText(message);
        else super.setText(super.getText() + '\n' + message);
    }

    /**
     * Updates the chat box wit the given message, along with who said it and the color to set the name and message
     * @param name The name of the person saying the message
     * @param nameColor The color of the name
     * @param message The message that the person sent
     * @param messageColor The color of the message
     */
    public void printMessage(String name, Color nameColor, String message, Color messageColor) {
        // TODO: Figure out how to set colors for text
        if (super.getText().equals("")) super.setText(name + ": " + message);
        else super.setText(super.getText() + '\n' + name + ": " + message);
    }
}
