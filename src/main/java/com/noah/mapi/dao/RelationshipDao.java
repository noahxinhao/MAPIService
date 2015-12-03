package com.noah.mapi.dao;


import com.noah.mapi.model.mongo.Relationship;

import java.util.List;

/**
 * Created by noahli on 15/9/20.
 */
public interface RelationshipDao {
    Relationship addRelationship(String userId, Relationship relationship);

    Relationship updateRelationship(Relationship relationship);

    Relationship getRelationShip(String userId);

    List<Relationship> getAllRelationship(String userId);
}
