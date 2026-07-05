/*
 * Author: Alex Zdanowicz
 */

package com.company;

import com.company.model.Model;
import com.company.view.Window;

import javax.swing.*;
import java.awt.*;

/**
 * The com.company.Controller class
 */
public class Controller {

    /**
     * The window of the chat client
     */
    private final Window window;

    /**
     * The model of the chat client
     */
    private final Model model;

    /**
     * The constructor for the Controller class
     */
    public Controller() {
        this.window = new Window(this);
        String name = JOptionPane.showInputDialog("Please input your name");
        this.model = new Model(this, name);
        this.window.setVisible(true);
    }

    /**
     * The method that is called when the message should be sent to the server
     * If the message is empty, it doesn't send it to the server
     */
    public void sendMessage() {
        String message = this.retrieveMessage();
        if (message.isEmpty()) return;

        this.model.sendMessage(message);
    }

    /**
     * Tells the window to print a message to the text box
     * @param message The message to print
     * @param messageColor The colour of the message
     */
    public void printMessage(String message, Color messageColor) {
        this.window.printMessage(message, messageColor);
    }

    /**
     * Tells the window to print a message to the text box
     * @param name The name of the person who sent the message
     * @param nameColor The colour to display the name
     * @param message The message to display
     * @param messageColor The colour of the message
     */
    public void printMessage(String name, Color nameColor, String message, Color messageColor) {
        this.window.printMessage(name, nameColor, message, messageColor);
    }

    /**
     * The method to call when there was an error with connecting to the server
     */
    public void declareConnectionError() {
        // TODO: Fill this in
    }

    /**
     * The method that retrieves the message that the user has typed
     * @return The message that the user has typed
     */
    private String retrieveMessage() {
        return this.window.retrieveMessage();
    }
}
