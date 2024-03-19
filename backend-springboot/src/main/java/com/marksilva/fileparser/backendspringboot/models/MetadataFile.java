package com.marksilva.fileparser.backendspringboot.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("metadataFile")
public class MetadataFile {
    @Id
    private ObjectId id;
//    private
}
