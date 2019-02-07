package Shared;

import java.io.Serializable;

public class CustomMessage implements Serializable {
    protected MessageType type;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getIntendedReceiver() {
        return intendedReceiver;
    }

    public void setIntendedReceiver(String intendedReceiver) {
        this.intendedReceiver = intendedReceiver;
    }

    protected String sender;
    protected String intendedReceiver;
}
