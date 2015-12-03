package com.noah.mapi.model;

/**
 * Created by noahli on 15/9/23.
 */
public class EventEnum {
    private EVENT event;

    public EVENT getEvent() {
        return event;
    }

    public void setEvent(EVENT event) {
        this.event = event;
    }

    public enum EVENT {
        sendMessage,
        socketRegister,
        messageRecived,
        friendApply
    }
}
