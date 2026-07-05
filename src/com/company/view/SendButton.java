/*
 * Author: Alex Zdanowicz
 */

package com.company.view;

import com.company.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The "Send" button in the bottom left-hand corner
 */
public class SendButton extends JButton {

    /**
     * The text to be displayed on the send button
     */
    public static final String TEXT = "Send";

    /**
     * The controller that holds and subscribes to the button
     */
    private final Controller controller;

    /**
     * The class that is used to encode what the button does when it is clicked
     */
    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            onClick();
        }
    }

    /**
     * The constructor for the send button
     * @param controller The controller that orchestrates all the components talking with each other
     */
    public SendButton(Controller controller) {
        super(SendButton.TEXT);
        super.addActionListener(new ClickListener());
        this.controller = controller;
    }

    /**
     * Sends the current message typed into the text box to the chat server when the button is clicked
     */
    private void onClick() {
        this.controller.sendMessage();
    }
}
