package com.marksilva.fileparser.backendspringboot.repositories;

import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecFileRepository extends MongoRepository<SpecFile, ObjectId> {
    Optional<SpecFile> findByName(String name);
}
