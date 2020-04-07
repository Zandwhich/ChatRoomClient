package com.company.view;

import com.company.Controller;

import javax.swing.*;

/**
 * The menu bar to show at the top of the window/application
 */
public class MenuBar extends JMenuBar {

    /* Fields */
    // Variables

    /**
     * The controller that controls everything
     */
    private Controller controller;

    /**
     * The menu that displays the participants in the chat
     */
    private ParticipantsMenu participantsMenu;


    /* Constructors */

    /**
     * The constructor for the menu bar
     * @param controller The controller that controls everything
     */
    public MenuBar(Controller controller) {
        this.controller = controller;
        this.participantsMenu = new ParticipantsMenu(controller);

        super.add(this.participantsMenu);
    }//end MenuBar()
}//end MenuBar
