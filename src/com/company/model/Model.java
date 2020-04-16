/*
 * Author: Alex Zdanowicz
 */

package com.company.model;

import com.company.Controller;
import json_simple.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

/**
 * The model class that is in charge with communicating with the server
 */
public class Model {

    /* Fields */

    // Constants

    /**
     * The key to denote the name in the JSON to the server
     */
    public static final String NAME_KEY = "name";

    /**
     * The key to denote the message in the JSON to the server
     */
    public static final String MESSAGE_KEY = "message";

    /**
     * The key to denote the time in the JSON to the server
     */
    public static final String TIME_KEY = "time";

    /**
     * The IP Address of the server to connect to
     */
    public static final String SERVER_IP = "10.0.1.40";

    /**
     * The starting port to try to connect to the server
     */
    public static final int START_PORT = 1024;

    /**
     * The highest port that we can connect to
     */
    public static final int MAX_PORT = 1124;

    // Variables

    /**
     * The controller that controls everything
     */
    private Controller controller;

    /**
     * The name of the client
     */
    private String name;

    /**
     * The socket that connects to the server
     */
    private Socket server;

    /**
     * The input stream to read in data from the server
     */
    private DataInputStream in;

    /**
     * The output stream to pass out data to the server
     */
    private DataOutputStream out;


    /* Constructors */

    /**
     * The constructor for the model
     * @param controller The controller that controls everything
     */
    public Model(Controller controller, String name) {
        this.controller = controller;
        this.name = name;

        // Do the initial connection with the server
        this.initialConnection();
    }//end Model()


    /* Methods */

    // Getters

    /**
     * Returns the name of the user
     * @return The name of the user
     */
    public String getName() {
        return name;
    }// end getName()


    // Public

    /**
     * Sends a message to the server along with other information
     * @param message The string message that the client wants to send to the server
     */
    public void sendMessage(String message) {
        JSONObject jsonObject = this.createJSONMessage(message);
        this.sendToServer(jsonObject.toString());

        // TODO: This is just here for testing purposes. Get rid of this later
        this.controller.printMessage(jsonObject.get(Model.NAME_KEY) + ": " + jsonObject.get(Model.MESSAGE_KEY));
    }//end sendMessage()

    // Private

    /**
     * Creates a JSON Object in the format specified in the README
     * @param message The message to attach
     * @return The constructed JSONObject with the message
     */
    private JSONObject createJSONMessage(String message) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(Model.NAME_KEY, this.name);
        jsonObject.put(Model.MESSAGE_KEY, message);
        jsonObject.put(Model.TIME_KEY, Calendar.getInstance().getTimeInMillis());

        return jsonObject;
    }//end createJSONWithMessage()

    /**
     * The method that generically sends a message to the server
     * @param message The message to send to the server
     */
    private void sendToServer(String message) {
        // TODO: This should be multithreaded
        try {
            this.out.writeUTF(message);
        } catch (IOException e) {
            System.err.println("There was an error sending a message to the server. Message:\n" + message);
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }//end try/catch
    }//end sendToServer

    /**
     * Runs once at the beginning to set the initial connection with the server
     */
    private void initialConnection() {

        // Connect to the server
        this.connectToServer();

        // If there was an error connecting to the server,
        //  let the user know
        if (this.server == null) {
            this.controller.declareConnectionError();
            return;
        }//end if

        // Create the input and output streams
        this.createInputOutputStreams();

        // If there was an error creating the streams,
        //  let the user know
        if (this.in == null || this.out == null) {
            this.controller.declareConnectionError();
            return;
        }//end if

        // Send the first message establishing the user's name
        this.sendFirstMessage();
    }//end initialConnection()

    /**
     * Connects to the server
     */
    private void connectToServer() {
        for (int port = Model.START_PORT; port < Model.MAX_PORT; port++) {
            try {
                this.server = new Socket(Model.SERVER_IP, port);
                break;
            } catch(UnknownHostException e) {
                // Increase to the next port; this one is taken
            } catch(IOException e) {
                // An unknown error happened
                System.err.println("An error occurred connecting to the server");
                System.err.println(e.getStackTrace());
                System.err.println(e.getMessage());
                this.server = null;
                break;
            }//end try/catch
        }//end for
    }//end connectToServer()

    /**
     * Creates the input and output streams with the server
     */
    private void createInputOutputStreams() {
        try {
            this.in = new DataInputStream(server.getInputStream());
            this.out = new DataOutputStream(server.getOutputStream());
        } catch (IOException e) {
            System.err.println("There was an error creating the input and/or output streams");
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            this.in = null;
            this.out = null;
        }//end try/catch
    }//end creatInputOutputStreams()

    /**
     * Sends the first establishing message to the server
     */
    private void sendFirstMessage() {
        // Create the message
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put(Model.NAME_KEY, this.name);
        jsonMessage.put(Model.TIME_KEY, Calendar.getInstance().getTimeInMillis());

        // Send the message
        this.sendToServer(jsonMessage.toString());
    }//end sendFirstMessage()

}//end Model
