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
}//end ChatBox
