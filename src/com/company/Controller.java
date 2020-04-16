package com.company;/*
 * Author: Alex Zdanowicz
 */

import com.company.model.Model;
import com.company.view.Window;

import javax.swing.*;

/**
 * The com.company.Controller class
 */
public class Controller {

    /* Fields */

    // Variables

    /**
     * The window of the chat client
     */
    private Window window;

    /**
     * The model of the chat client
     */
    private Model model;


    /* Constructors */

    /**
     * The constructor for the Controller class
     */
    public Controller() {
    }//end Controller()


    /* Methods */

    // Public

    /**
     * The method that runs the program
     */
    public void run() {
        String name = JOptionPane.showInputDialog("Please input your name");
        this.model = new Model(this, name);
        this.window = new Window(this);

        // TODO: Fill this in more when we get here
    }//end run()

    /**
     * The method that is called when the message should be sent to the server
     * If the message is empty, it doesn't send it to the server
     */
    public void sendMessage() {
        String message = this.retrieveMessage();

        // Check to see if the message is empty
        if (message.equals("")) return;

        this.model.sendMessage(message);
    }//end sendMessage()

    /**
     * Tells the window to print a message to the text box
     * @param message The message to print
     */
    public void printMessage(String message) {
        // TODO: In the future parse out the person's name and figure out the colour to set their name, etc.

        this.window.printMessage(message);
    }//end printMessage()

    /**
     * The method to call when there was an error with connecting to the server
     */
    public void declareConnectionError() {
        // TODO: Fill this in
    }//end declareConnectionError()

    // Private

    /**
     * The method that retrieves the message that the user has typed
     * @return The message that the user has typed
     */
    private String retrieveMessage() {
        return this.window.retrieveMessage();
    }//end retrieveMessage()

}//end com.company.Controller
