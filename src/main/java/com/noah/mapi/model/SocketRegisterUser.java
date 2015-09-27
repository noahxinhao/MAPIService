package com.noah.mapi.model;

/**
 * Created by noahli on 15/9/22.
 */
public class SocketRegisterUser {
    private String userId;

    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public SocketRegisterUser(){

    }

    public SocketRegisterUser(String userId, String sessionId){
        super();
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
