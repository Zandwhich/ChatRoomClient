# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Java chat room client that connects to a companion [ChatRoomServer](https://github.com/Zandwhich/ChatRoomServer). Uses Swing for the GUI and json_simple for JSON serialization over TCP sockets.

## Build & Run

This is an IntelliJ IDEA project (JDK 23) with no Maven/Gradle. Source root is `src/`.

```bash
# Compile
javac -d out/production/ChatRoomClient $(find src -name "*.java")

# Run (server must be running first on 127.0.1.1:1024)
java -cp out/production/ChatRoomClient com.company.Main
```

There are no tests or linters configured.

## Architecture

MVC pattern with a single `Controller` coordinating between `Model` and view components:

- **Controller** — instantiates Model and Window, routes messages between them. Entry point creates it and calls `run()`.
- **Model** — manages the TCP socket connection to the server. Spawns an `InputThread` that loops reading JSON messages from the server and dispatches them to the Controller for display. Outgoing messages are serialized as JSON with keys `name`, `message`, `time`.
- **View** (`com.company.view`) — Swing components: `Window` (JFrame), `ChatBox` (display pane), `InputArea` (text input), `SendButton` (triggers send via Controller), `MenuBar`/`ParticipantsMenu`.

## JSON Protocol

Messages to/from server use keys defined as constants in `Model`: `"name"`, `"message"`, `"text"`, `"time"`. The first message sent on connect contains only `name` and `time` to register the client.

## Dependencies

`json_simple` is vendored in `src/json_simple/` — not an external dependency.
