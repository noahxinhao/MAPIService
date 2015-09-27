package com.noah.mapi.dao.impl;
import com.noah.mapi.dao.MessageDao;
import com.noah.mapi.model.mongo.Message;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by noahli on 15/9/19.
 */
@Repository
public class MessageDaoImpl implements MessageDao {

    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public Message addMessage(Message message) {
        if (StringUtils.isBlank(message.getId())) {
            message.setId(UUID.randomUUID().toString().replace("-", ""));
        }
        if (null == message.getCreateDate()) {
            message.setCreateDate(new Date());
        }
        mongoTemplate.insert(message);
        return message;
    }

    public List<Message> getMessageBy(String uid) {
        Criteria criteria = null;
        criteria = criteria.where("toUserId").is(uid);
        criteria = criteria.where("status").is(Message.STATUS.未发送);
        Query query = new Query(criteria);
        List<Message> messages = mongoTemplate.find(query, Message.class);
        return messages;
    }

    @Override
    public Message updateMessage(Message message) {
        Query query = new Query(Criteria.where("_id").is(message.getId()));
        Update update = new Update();
        update.set("status",message.getStatus().name());

        mongoTemplate.updateFirst(query, update, Message.class);
        return message;

    }
}
