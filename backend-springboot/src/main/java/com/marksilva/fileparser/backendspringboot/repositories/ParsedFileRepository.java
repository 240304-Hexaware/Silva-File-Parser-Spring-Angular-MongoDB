package com.marksilva.fileparser.backendspringboot.repositories;

import com.marksilva.fileparser.backendspringboot.models.ParsedFile;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParsedFileRepository extends MongoRepository<ParsedFile, ObjectId> {

}
