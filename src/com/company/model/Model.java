/*
 * Author: Alex Zdanowicz
 */

package com.company.model;

import com.company.controller.Controller;
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

    /**
     * The thread to deal with inputs from the server
     */
    private class InputThread extends Thread {

        /**
         * The JSONParser used in parsing Strings into JSONObjects
         */
        JSONParser parser;

        /**
         * The constructor for the input thread
         * @param threadName The name of the thread
         */
        public InputThread(String threadName) {
            super(threadName);
            this.parser = new JSONParser();
        }

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
                }

                JSONObject jsonResponse = this.deserializeMessageFromServer(input);

                // There was an error with reading in this message; skipping over it and continuing on
                if (jsonResponse == null) continue;

                this.outputMessage(jsonResponse);
            }
        }

        /**
         * Gets the data from the server
         * @return The input from the server
         * @throws IOException TODO: Fill in
         */
        private String readInput() throws IOException {
            return in.readUTF();
        }

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
            }
            return jsonObject;
        }

        /**
         * Outputs the response from the server onto the screen.
         * The response should be in the format specified in the README
         * @param response The response from the server
         */
        private void outputMessage(JSONObject response) {
            String messageText = this.extractText(response, Model.MESSAGE_KEY);
            if (messageText == null) {
                this.malformedServerResponse(response);
                return;
            }

            // A response without a name is a system message and is printed on its own
            if (!response.containsKey(Model.NAME_KEY) || response.get(Model.NAME_KEY) == null) {
                controller.printMessage(messageText, Color.BLACK);
                return;
            }

            String nameText = this.extractText(response, Model.NAME_KEY);
            if (nameText == null) {
                this.malformedServerResponse(response);
                return;
            }

            controller.printMessage(nameText, Color.BLUE, messageText, Color.BLACK);
        }

        /**
         * Pulls the text out of a nested object (keyed by TEXT_KEY) within the response
         * @param response The response from the server
         * @param key The key of the nested object to read
         * @return The text within the nested object, or null if it is missing or malformed
         */
        private String extractText(JSONObject response, String key) {
            if (!response.containsKey(key) || response.get(key) == null) return null;

            JSONObject nested = (JSONObject) response.get(key);
            if (!nested.containsKey(Model.TEXT_KEY) || nested.get(Model.TEXT_KEY) == null) return null;

            return (String) nested.get(Model.TEXT_KEY);
        }

        /**
         * Prints out errors if there is a malformed response from the server
         * @param response The malformed response from the server
         */
        private void malformedServerResponse(JSONObject response) {
            System.err.println("There was a malformed response from the server");
            System.err.println("Malformed response: " + response.toString());
        }
    }

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
    public static final String SERVER_IP = "127.0.1.1";

    /**
     * The starting port to try to connect to the server
     */
    public static final int START_PORT = 1024;

    /**
     * The name of the input thread
     */
    public static final String INPUT_THREAD_NAME = "Input Thread";

    /**
     * The controller that controls everything
     */
    private final Controller controller;

    /**
     * The name of the client
     */
    private final String name;

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

    /**
     * The constructor for the model
     * @param controller The controller that controls everything
     * @param name The name of the client
     */
    public Model(Controller controller, String name) {
        this.controller = controller;
        this.name = name;

        this.initialConnection();

        InputThread inputThread = new InputThread(Model.INPUT_THREAD_NAME);
        inputThread.start();
    }

    /**
     * Sends a message to the server along with other information
     * @param message The string message that the client wants to send to the server
     */
    public void sendMessage(String message) {
        JSONObject jsonObject = this.createJSONMessage(message);
        this.sendToServer(jsonObject.toString());
    }

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
    }

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
        }
    }

    /**
     * Runs once at the beginning to set the initial connection with the server
     */
    private void initialConnection() {
        this.connectToServer();
        if (this.server == null) {
            this.controller.declareConnectionError();
            return;
        }

        this.createInputOutputStreams();
        if (this.in == null || this.out == null) {
            this.controller.declareConnectionError();
            return;
        }

        this.sendFirstMessage();
    }

    /**
     * Connects to the server
     */
    private void connectToServer() {
        try {
            System.out.println("Attempting to connect on port: " + Model.START_PORT);
            this.server = new Socket(Model.SERVER_IP, Model.START_PORT);
        } catch (Exception e) {
            System.err.println("An error occurred connecting to the server");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Cause: " + e.getCause());
            System.err.println("Stack Trace:");
            e.printStackTrace();
            this.server = null;
        }
    }

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
        }
    }

    /**
     * Sends the first establishing message to the server
     */
    private void sendFirstMessage() {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put(Model.NAME_KEY, this.name);
        jsonMessage.put(Model.TIME_KEY, Calendar.getInstance().getTimeInMillis());

        this.sendToServer(jsonMessage.toString());
    }
}
