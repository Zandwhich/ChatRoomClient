package com.company.view;

import com.company.controller.Controller;

import javax.swing.*;

/**
 * The class to hold all the names of the participants in the MenuBar
 */
public class ParticipantsMenu extends JMenu {

    /**
     * The name of the menu
     */
    public static final String NAME = "Participants";

    /**
     * The placeholder text shown until the participants list is implemented
     */
    public static final String STAND_IN_TEXT = "Coming Soon!";

    /**
     * The constructor for this object
     */
    public ParticipantsMenu() {
        super(ParticipantsMenu.NAME);
        super.add(ParticipantsMenu.STAND_IN_TEXT);
    }
}
