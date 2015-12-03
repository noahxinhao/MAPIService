package com.noah.mapi.model.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by noahli on 15/9/19.
 */
@Document
public class Message {
    @Id
    private String id = UUID.randomUUID().toString();
    private Date createDate = new Date();
    private String fromUserId;
    private String toUserId;
    private Map detail;
    private TYPE type = null;

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    private STATUS status = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public Map getDetail() {
        return detail;
    }

    public void setDetail(Map detail) {
        this.detail = detail;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public enum TYPE {
        系统消息,
        好友请求,
        聊天消息,
        推广
    }

    public enum STATUS {
        未发送,
        已发送,
        已读取
    }
}
