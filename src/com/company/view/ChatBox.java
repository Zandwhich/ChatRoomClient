package com.company.view;

import com.company.Controller;

import javax.swing.*;

/**
 * The box that shows the chat
 */
public class ChatBox extends JTextPane {

    /* Fields */
    // Variables

    /**
     * The controller
     */
    private Controller controller;


    /* Constructors */

    /**
     * The constructor for the chat box
     * @param controller The controller
     */
    public ChatBox(Controller controller) {
        this.controller = controller;

        super.setEditable(false);
    }//end ChatBox()


    /* Methods */

    // Public

    /**
     * Updates the chat box with the given message
     * @param message The given message to print to the screen
     */
    public void printMessage(String message) {
        if (super.getText().equals("")) super.setText(message);
        else super.setText(super.getText() + '\n' + message);
    }//end printMessage()
}//end ChatBox
