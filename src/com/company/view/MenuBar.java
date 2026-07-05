package com.company.view;

import com.company.controller.Controller;
import javax.swing.*;

/**
 * The menu bar to show at the top of the window/application
 */
public class MenuBar extends JMenuBar {

    /**
     * The menu that displays the participants in the chat
     */
    private final ParticipantsMenu participantsMenu;

    /**
     * The constructor for the menu bar
     */
    public MenuBar() {
        this.participantsMenu = new ParticipantsMenu();
        super.add(this.participantsMenu);
    }
}
