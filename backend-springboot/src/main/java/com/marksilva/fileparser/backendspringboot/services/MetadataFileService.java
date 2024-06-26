package com.marksilva.fileparser.backendspringboot.services;

import com.marksilva.fileparser.backendspringboot.exceptions.ResourceNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.MetadataFile;
import com.marksilva.fileparser.backendspringboot.models.ParsedFile;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.repositories.MetadataRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetadataFileService {
    private MetadataRepository metadataRepository;

    @Autowired
    public MetadataFileService(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    public MetadataFile findById(ObjectId metadataId) throws ResourceNotFoundException {
        return this.metadataRepository.findById(metadataId).orElseThrow(() ->
                new ResourceNotFoundException("Cannot find Metadata with the given id: " + metadataId.toString())
        );
    }

    public MetadataFile insertMetadataFileLocal(MetadataFile metadataFile, String filePath) {
        metadataFile.setLocalPathToOrgFile(filePath);
        return this.metadataRepository.insert(metadataFile);
    }

    public List<MetadataFile> insertListOfMetadataFileLocal(List<ParsedFile> listOfParsedFiles, String filePath) {
        List<MetadataFile> listOfMetadataFiles = new ArrayList<>();
        for (ParsedFile ps : listOfParsedFiles) {
            listOfMetadataFiles.add(new MetadataFile(ps.getId(), filePath, LocalDate.now()));
        }
        return this.metadataRepository.saveAll(listOfMetadataFiles);
    }
}
