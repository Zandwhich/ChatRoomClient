/*
 * Author: Alex Zdanowicz
 */

package com.company.view;



import com.company.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The "Send" button in the bottom left-hand corner
 */
public class SendButton extends JButton {

    /* Fields */

    // Constants

    /**
     * The default width of button button
     */
    public static final int DEFAULT_WIDTH = 30;

    /**
     * The default height of the button
     */
    public static final int DEFAULT_HEIGHT = 10;

    /**
     * The text to be displayed on the send button
     */
    public static final String text = "Send";

    // Variables

    /**
     * The controller that holds and subscribes to the button
     */
    private Controller controller;

    /**
     * The window that holds the button
     */
    private Window window;

    /**
     * The class that is used to encode what the button does when it is clicked
     */
    private class ClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            onClick(e);
        }//end actionPerformed()
    }//end ClickListener


    /* Constructors */

    /**
     * The constructor for the send button
     * @param controller The controller that orchestrates all of the components talking with each other
     * @param window The window that holds the send button
     */
    public SendButton(Controller controller, Window window) {
        super(SendButton.text);
        super.addActionListener(new ClickListener());

        this.controller = controller;
        this.window = window;
    }//end SendButton()

    /* Methods */

    // Protected

    /**
     * Sends the current message typed into the text box to the chat server when the button is clicked
     */
    private void onClick(ActionEvent e) {
        this.controller.sendMessage();
    }//end onClick()

}//end SendButton
