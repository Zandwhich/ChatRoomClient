package com.company;/*
 * Author: Alex Zdanowicz
 */

import com.company.view.Window;

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
     * The name of the person using the chat client
     */
    private String name;


    /* Constructors */

    /**
     * The constructor for the Controller class
     */
    public Controller() {
    }//end Controller()


    /* Methods */

    // Public
    // Getters

    /**
     * Returns the name of the person using this chat client
     * @return The name of the person using this chat client
     */
    public String getName() {
        return name;
    }//end getName()

    /**
     * The method that runs the program
     */
    public void run() {
        this.window = new Window(this);

        // TODO: Fill this in more when we get here
    }//end run()

    /**
     * The method that is called when the message should be sent to the server
     */
    public void sendMessage() {
        // TODO: Fill this in
    }//end sendMessage()

    /**
     * Tells the window to print a message to the text box
     * @param message The message to print
     */
    public void printMessage(String message) {
        // TODO: In the future parse out the person's name and figure out the colour to set their name, etc.

        this.window.printMessage(message);
    }//end printMessage()

    // Private

}//end com.company.Controller
