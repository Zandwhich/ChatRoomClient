/*
 * Author: Alex Zdanowicz
 */

package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

/**
 * The main class to start off the application
 */
public class Main {

    /**
     * The main method to start off the application
     * @param args arguments
     */
    public static void main(String[] args) {

        Controller controller = new Controller();
        controller.run();

    }//end main()
}//end com.company.Main
