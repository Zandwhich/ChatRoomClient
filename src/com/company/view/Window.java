/*
 * Author: Alex Zdanowicz
 */

package com.company.view;

import com.company.Controller;

import javax.swing.*;

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
     * The main panel that holds everything in the window together
     */
    private JPanel mainPanel;

    /**
     * The panel at the bottom that holds the input text and the send button
     */
    private JPanel bottomPanel;


    /* Constructors */

    /**
     * The constructor for the window
     * @param controller The controller that puts everything together
     */
    public Window(Controller controller) {
        super(Window.TITLE);

        this.controller = controller;
        this.sendButton = new SendButton(this.controller, this);
        this.menuBar = new MenuBar(this.controller);

        this.mainPanel = new JPanel();
        this.bottomPanel = new JPanel();

        this.bottomPanel.add(this.sendButton);
        this.mainPanel.add(this.bottomPanel);
        super.add(this.mainPanel);
        super.setJMenuBar(this.menuBar);

        super.setSize(Window.DEFAULT_WIDTH, Window.DEFAULT_HEIGHT);
        // TODO: Set it so that it disconnects from the server
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TODO: We only have this here now for testing purposes.
        //  In the future, this screen will only become visible once the user inputs their name
        super.setVisible(true);
    }//end Window()

    /* Methods */

    public void printMessage(String message) {
        // TODO: Fill in once we've got the text box
    }
}//end Window
