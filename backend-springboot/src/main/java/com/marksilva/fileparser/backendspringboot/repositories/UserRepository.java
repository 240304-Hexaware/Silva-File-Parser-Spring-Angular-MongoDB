package com.marksilva.fileparser.backendspringboot.repositories;

import com.marksilva.fileparser.backendspringboot.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    /*
    <S extends T> S save(S entity);
    Optional<T> findById(ID primaryKey);
    Iterable<T> findAll();
    long count();
    void delete(T entity);
    boolean existsById(ID primaryKey);
     */

    Optional<User> findByUsername(String username);
    Boolean existsUserByUsername(String username);
}
