package com.marksilva.fileparser.backendspringboot.repositories;

import com.marksilva.fileparser.backendspringboot.models.ParsedFile;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParsedFileRepository extends MongoRepository<ParsedFile, ObjectId> {
    List<ParsedFile> findByUserId(ObjectId userId);
    List<ParsedFile> findByUserIdAndSpecId(ObjectId userId, ObjectId specId);
}