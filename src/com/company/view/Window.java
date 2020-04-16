/*
 * Author: Alex Zdanowicz
 */

package com.company.view;

import com.company.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * The main window of the chat room
 */
public class Window extends JFrame {

    /* Fields */

    // Constants

    /**
     * The default height of the chat window in pixels
     */
    public static int DEFAULT_HEIGHT = 500;

    /**
     * The default width of the chat window in pixels
     */
    public static int DEFAULT_WIDTH = 350;

    /**
     * The default title of the chat client
     */
    public static String TITLE = "Alex's Super Cool Chat Client";

    // Variables

    /**
     * The controller that orchestrates everything together
     */
    private Controller controller;

    /**
     * The menu bar at the top of the screen
     */
    private MenuBar menuBar;

    /**
     * The button to send messages
     */
    private SendButton sendButton;

    /**
     * The input field at the bottom of the window
     */
    private InputArea inputArea;

    /**
     * The main panel that holds everything in the window together
     */
    private JPanel mainPanel;

    /**
     * The panel at the bottom that holds the input text and the send button
     */
    private JPanel bottomPanel;

    /**
     * The pane that will hold the text of the chat
     */
    private JScrollPane chatScrollPane;

    /**
     * The pane that will hold the box to type out the message
     */
    private JScrollPane messageScrollPane;

    /**
     * The chat box where the chat will be displayed
     */
    private ChatBox chatBox;


    /* Constructors */

    /**
     * The constructor for the window
     * @param controller The controller that puts everything together
     */
    public Window(Controller controller) {
        super(Window.TITLE);
        super.setSize(Window.DEFAULT_WIDTH, Window.DEFAULT_HEIGHT);
        super.setResizable(false);

        this.controller = controller;
        this.sendButton = new SendButton(this.controller, this);
        this.menuBar = new MenuBar(this.controller);
        this.inputArea = new InputArea(this.controller);
        this.chatBox = new ChatBox(this.controller);
        this.chatScrollPane = new JScrollPane(this.chatBox);
        this.messageScrollPane = new JScrollPane(this.inputArea);
        this.bottomPanel = new JPanel(new BorderLayout(2, 0));
        this.mainPanel = new JPanel(new BorderLayout(0, 2));

        this.bottomPanel.add(this.messageScrollPane, BorderLayout.CENTER);
        this.bottomPanel.add(this.sendButton, BorderLayout.LINE_END);

//        this.scrollPane.setSize(50, 50);

        this.mainPanel.add(this.chatScrollPane, BorderLayout.CENTER);
        this.mainPanel.add(this.bottomPanel, BorderLayout.PAGE_END);
//        this.mainPanel.add(this.bottomPanel, BorderLayout.SOUTH);

        super.add(this.mainPanel);

        super.setJMenuBar(this.menuBar);


        // TODO: Set it so that it disconnects from the server
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TODO: We only have this here now for testing purposes.
        //  In the future, this screen will only become visible once the user inputs their name
        super.setVisible(true);
    }//end Window()

    /* Methods */

    /**
     * Prints another message to the screen
     * @param message The message to print to the screen
     */
    public void printMessage(String message) {
        this.chatBox.printMessage(message);
    }//end printMessage()

    /**
     * Gets the message that the user has typed and clears out the input box
     * @return Returns the message that the user has typed out and clears the message box
     */
    public String retrieveMessage() {
        String message = this.inputArea.getText();
        this.inputArea.setText("");
        return message;
    }//end retrieveMessage()
}//end Window
