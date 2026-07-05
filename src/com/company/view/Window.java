/*
 * Author: Alex Zdanowicz
 */

package com.company.view;

import com.company.controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * The main window of the chat room
 */
public class Window extends JFrame {

    /**
     * The default height of the chat window in pixels
     */
    public static final int DEFAULT_HEIGHT = 500;

    /**
     * The default width of the chat window in pixels
     */
    public static final int DEFAULT_WIDTH = 350;

    /**
     * The default title of the chat client
     */
    public static final String TITLE = "Alex's Super Cool Chat Client";

    /**
     * The controller that orchestrates everything together
     */
    private final Controller controller;

    /**
     * The menu bar at the top of the screen
     */
    private final MenuBar menuBar;

    /**
     * The button to send messages
     */
    private final SendButton sendButton;

    /**
     * The input field at the bottom of the window
     */
    private final InputArea inputArea;

    /**
     * The main panel that holds everything in the window together
     */
    private final JPanel mainPanel;

    /**
     * The panel at the bottom that holds the input text and the send button
     */
    private final JPanel bottomPanel;

    /**
     * The pane that will hold the text of the chat
     */
    private final JScrollPane chatScrollPane;

    /**
     * The pane that will hold the box to type out the message
     */
    private final JScrollPane messageScrollPane;

    /**
     * The chat box where the chat will be displayed
     */
    private final ChatBox chatBox;

    /**
     * The constructor for the window
     * @param controller The controller that puts everything together
     */
    public Window(Controller controller) {
        super(Window.TITLE);

        this.controller = controller;
        this.sendButton = new SendButton(this.controller);
        this.menuBar = new MenuBar();
        this.inputArea = new InputArea();
        this.chatBox = new ChatBox();
        this.chatScrollPane = new JScrollPane(this.chatBox);
        this.messageScrollPane = new JScrollPane(this.inputArea);
        this.bottomPanel = new JPanel(new BorderLayout(2, 0));
        this.mainPanel = new JPanel(new BorderLayout(0, 2));

        this.layoutComponents();
        this.configureFrame();
    }

    /**
     * Prints a message to the screen
     * @param message The message to print to the screen
     * @param messageColor The colour of the message
     */
    public void printMessage(String message, Color messageColor) {
        this.chatBox.printMessage(message, messageColor);
    }

    /**
     * Prints a message with someone's name to print first and the appropriate colours
     * @param name The name of the person to print first
     * @param nameColor The colour of the name of the person
     * @param message The message to print
     * @param messageColor The colour of the message
     */
    public void printMessage(String name, Color nameColor, String message, Color messageColor) {
        this.chatBox.printMessage(name, nameColor, message, messageColor);
    }

    /**
     * Gets the message that the user has typed and clears out the input box
     * @return Returns the message that the user has typed out and clears the message box
     */
    public String retrieveMessage() {
        String message = this.inputArea.getText();
        this.inputArea.setText("");
        return message;
    }

    /**
     * Assembles the panels and adds them to the frame
     */
    private void layoutComponents() {
        this.bottomPanel.add(this.messageScrollPane, BorderLayout.CENTER);
        this.bottomPanel.add(this.sendButton, BorderLayout.LINE_END);

        this.mainPanel.add(this.chatScrollPane, BorderLayout.CENTER);
        this.mainPanel.add(this.bottomPanel, BorderLayout.PAGE_END);

        super.add(this.mainPanel);
        super.setJMenuBar(this.menuBar);
    }

    /**
     * Applies the frame-level configuration
     */
    private void configureFrame() {
        super.setSize(Window.DEFAULT_WIDTH, Window.DEFAULT_HEIGHT);
        super.setResizable(false);

        // TODO: Set it so that it disconnects from the server
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TODO: We only have this here now for testing purposes.
        //  In the future, this screen will only become visible once the user inputs their name
        super.setVisible(false);
    }
}
