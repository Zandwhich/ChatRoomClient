package com.company;/*
 * Author: Alex Zdanowicz
 */

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

//        try {
//            // For now I'm simply going to hit the server
//            Socket server  = new Socket("10.0.1.36", 1024);
//            DataInputStream in = new DataInputStream(server.getInputStream());
//            DataOutputStream out = new DataOutputStream(server.getOutputStream());
//
//            System.out.println(in.readUTF());
//
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//            System.err.println(e.getStackTrace());
//        }//end try/catch
    }//end main()
}//end com.company.Main
