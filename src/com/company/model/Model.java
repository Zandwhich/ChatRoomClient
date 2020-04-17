/*
 * Author: Alex Zdanowicz
 */

package com.company.model;

import com.company.Controller;
import json_simple.JSONObject;
import json_simple.parser.JSONParser;
import json_simple.parser.ParseException;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.net.*;
import java.util.Calendar;

/**
 * The model class that is in charge with communicating with the server
 */
public class Model {

    /* Internal Classes */

    /**
     * The thread to deal with inputs from the server
     */
    private class InputThread extends Thread {

        /* Fields */

        // Variables
        /**
         * The JSONParser used in parsing Strings into JSONObjects
         */
        JSONParser parser;

        /* Constructors */

        /**
         * The constructor for the input thread
         * @param threadName The name of the thread
         */
        public InputThread(String threadName) {
            super(threadName);
            this.parser = new JSONParser();
        }//end InputThread()

        /* Methods */

        /**
         * Deals with receiving inputs from the server
         */
        @Override
        public void run() {
            String input;

            while (true) {
                try {
                    input = this.readInput();
                } catch (IOException e) {
                    System.err.println("There was an error while trying to read in data from the server");
                    System.err.println("Message: " + e.getMessage());
                    System.err.println("Cause: " + e.getCause());
                    System.err.println("Stack Trace:"); e.printStackTrace();
                    break;
                }//end try/catch

                JSONObject jsonResponse  = this.deserializeMessageFromServer(input);

                // There was an error with reading in this message; skipping over it and continuing on
                if (jsonResponse == null) continue;

                this.outputMessage(jsonResponse);
            }//end while
        }//end run()

        /**
         * Gets the data from the server
         * @return The input from the server
         * @throws IOException TODO: Fill in
         */
        private String readInput() throws IOException {
            return in.readUTF();
        }//end readInput()

        /**
         * Deserializes a message from the server from a String into a JSONObject
         * @param message The JSON object received from the server as a String
         * @return The JSON object received from the server as a JSONObject
         */
        private JSONObject deserializeMessageFromServer(String message) {
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) this.parser.parse(message);
            } catch (ParseException e) {
                System.err.println("An error occurred while trying to parse a message from the server");
                System.err.println("Message from server: " + message);
                System.err.println("Error Message: " + e.getMessage());
                System.err.println("Cause of error: " + e.getCause());
                System.err.println("Stack Trace:"); e.printStackTrace();
            }//end try/catch
            return jsonObject;
        }//end deserializeMessageFromServer

        /**
         * Outputs the response from the server onto the screen.
         * The response should be in the format specified in the README
         * @param response The response from the server
         */
        private void outputMessage(JSONObject response) {
            if (!response.containsKey(Model.MESSAGE_KEY) || response.get(Model.MESSAGE_KEY) == null) {
                this.malformedServerResponse(response);
                return;
            }//end if

            JSONObject message = (JSONObject) response.get(Model.MESSAGE_KEY);

            if (!message.containsKey(Model.TEXT_KEY) || message.get(Model.TEXT_KEY) == null) {
                this.malformedServerResponse(response);
                return;
            }//end if

            String messageText = (String) message.get(Model.TEXT_KEY);

            // TODO: Implement the colour for the message

            if (!response.containsKey(Model.NAME_KEY) || response.get(Model.NAME_KEY) == null) {
                controller.printMessage(messageText, Color.BLACK);
                return;
            }//end if

            JSONObject name = (JSONObject) response.get(Model.NAME_KEY);

            if (!name.containsKey(Model.TEXT_KEY) || name.get(Model.TEXT_KEY) == null) {
                this.malformedServerResponse(response);
                return;
            }//end if

            String nameText = (String) name.get(Model.TEXT_KEY);

            // TODO: Implement the colour for the message

            controller.printMessage(nameText, Color.BLUE, messageText, Color.BLACK);
        }//end outputMessage()

        /**
         * Prints out errors if there is a malformed response from the server
         * @param response The malformed response from the server
         */
        private void malformedServerResponse(JSONObject response) {
            System.err.println("There was a malformed response from the server");
            System.err.println("Malformed response: " + response.toString());
        }//end malformedServerResponse
    }//end InputThread


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
     * The key to denote the text in the JSON object
     */
    public static final String TEXT_KEY = "text";

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

    /**
     * The timeout tolerance in milliseconds for connecting to the server
     */
    public static final int TIMEOUT = 100;

    /**
     * The name of the input thread
     */
    public static final String INPUT_THREAD_NAME = "Input Thread";

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

        InputThread inputThread = new InputThread(Model.INPUT_THREAD_NAME);
        inputThread.start();
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
            System.err.println("Error Message: " + e.getMessage());
            System.err.println("Cause: " + e.getCause());
            System.err.println("Stack Trace:"); e.printStackTrace();
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
        this.server = new Socket();
        for (int port = Model.START_PORT; port <= Model.MAX_PORT; port++) {
            try {
                System.out.println("Attempting to connect on port: " + port);
                InetSocketAddress address = new InetSocketAddress(Model.SERVER_IP, port);
                this.server.connect(address, 10);
                //this.server = new Socket(Model.SERVER_IP, port);
                break;
            } catch (SocketTimeoutException e) {
                // Increasing the port #
            } catch(Exception e) {
                // An unknown error happened
                System.err.println("An error occurred connecting to the server");
                System.err.println("Message: " + e.getMessage());
                System.err.println("Cause: " + e.getCause());
                System.err.println("Stack Trace:");
                e.printStackTrace();
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
            System.err.println("Error Message: " + e.getMessage());
            System.err.println("Error Cause: " + e.getCause());
            System.err.println("Stack Trace:"); e.printStackTrace();
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
