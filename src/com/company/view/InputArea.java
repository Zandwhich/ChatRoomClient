package com.company.view;

import com.company.Controller;

import javax.swing.*;

/**
 * Class that handles inputting text
 */
public class InputArea extends JTextArea {

    /**
     * The number of columns for the text field to take up
     */
    public static final int NUM_OF_COLS = 22;

    /**
     * The default message that will be in the input box
     */
    public static final String DEFAULT_MESSAGE = "Type a message...";

    /**
     * The controller
     */
    private Controller controller;

    /**
     * The constructor for the InputField
     * @param controller The controller
     */
    public InputArea(Controller controller) {
        //super(InputField.DEFAULT_MESSAGE, InputField.NUM_OF_COLS);
        this.controller = controller;
    }
}
