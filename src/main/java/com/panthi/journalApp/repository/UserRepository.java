package com.panthi.journalApp.repository;

import com.panthi.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository <User, ObjectId> {
    User findByUserName(String name);
    void deleteByUserName(String name);
}
