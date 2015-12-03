package com.noah.mapi.model.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * Created by noahli on 15/9/20.
 */
@Document
public class Friend {
    @Id
    private String id;
    private String name;
    private String userId;
    private Status status = Status.正常;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public enum Status {
        正常,
        待验证,
        删除
    }
}
