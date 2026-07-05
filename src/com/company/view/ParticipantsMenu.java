package com.company.view;

import com.company.Controller;

import javax.swing.*;

/**
 * The class to hold all of the names of the participants in the MenuBar
 */
public class ParticipantsMenu extends JMenu {

    /**
     * The name of the menu
     */
    public static final String NAME = "Participants";

    public static final String stand_in_text = "Coming Soon!";

    private Controller controller;

    /**
     * The constructor for this object
     * @param controller The controller that orchestrates everything
     */
    public ParticipantsMenu(Controller controller) {
        super(ParticipantsMenu.NAME);

        this.controller = controller;
        super.add(ParticipantsMenu.stand_in_text);
    }
}
