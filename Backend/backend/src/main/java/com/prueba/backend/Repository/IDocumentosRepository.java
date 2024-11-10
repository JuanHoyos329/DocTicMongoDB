package com.prueba.backend.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prueba.backend.Model.DocumentosModel;

@Repository
public interface IDocumentosRepository extends MongoRepository<DocumentosModel, ObjectId>{
    
}
