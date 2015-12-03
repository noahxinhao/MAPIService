package com.noah.mapi.dao.impl;

import com.noah.mapi.dao.RelationshipDao;
import com.noah.mapi.model.mongo.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by noahli on 15/9/20.
 */
@Service
public class RelationshipDaoImpl implements RelationshipDao {

    @Autowired
    MongoTemplate messageTemplate;

    @Override
    public Relationship addRelationship(String userId, Relationship relationship) {
        messageTemplate.insert(relationship);
        return relationship;
    }

    @Override
    public Relationship updateRelationship(Relationship relationship) {
        Query query = new Query(Criteria.where("_id").is(relationship.getId()));
        return null;
    }

    @Override
    public Relationship getRelationShip(String userId) {
        return null;
    }

    @Override
    public List<Relationship> getAllRelationship(String userId) {
        return null;
    }
}
