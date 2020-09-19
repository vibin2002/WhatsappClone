package com.android.whatsappclone;

public class Chat {
    private String sender;

    public Chat() {
    }

    private String reciever;
    private String message;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Chat(String message, String reciever, String sender) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
    }
}
