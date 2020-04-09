package com.company.view;

import com.company.Controller;

import javax.swing.*;

/**
 * Class that handles inputting text
 */
public class InputArea extends JTextArea {

    /* Fields */
    // Constants

    /**
     * The number of columns for the text field to take up
     */
    public static final int NUM_OF_COLS = 22;

    /**
     * The default message that will be in the input box
     */
    public static final String DEFAULT_MESSAGE = "Type a message...";

    // Variables

    /**
     * The controller
     */
    private Controller controller;


    /* Constructors */

    /**
     * The constructor for the InputField
     * @param controller The controller
     */
    public InputArea(Controller controller) {
        //super(InputField.DEFAULT_MESSAGE, InputField.NUM_OF_COLS);
        this.controller = controller;
    }//end InputField()
}//end InputField
