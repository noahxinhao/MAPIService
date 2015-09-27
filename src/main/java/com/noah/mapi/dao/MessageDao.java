package com.noah.mapi.dao;


import com.noah.mapi.model.mongo.Message;

import java.util.List;

/**
 * Created by noahli on 15/9/19.
 */
public interface MessageDao {
    Message addMessage(Message message);
    List<Message> getMessageBy(String uid);
    Message updateMessage(Message message);
}
