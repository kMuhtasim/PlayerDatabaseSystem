package com.example.playerdatabasesystem;

import java.io.Serializable;

public class Message implements Serializable {
    String action;
    String string;

    Message() {}
    Message(String action) {
        this.action = action;
    }
    Message(String action, String string) {
        this.action = action;
        this.string = string;
    }
}
