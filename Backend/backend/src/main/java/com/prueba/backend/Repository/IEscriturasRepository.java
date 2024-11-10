package com.prueba.backend.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.prueba.backend.Model.Escrituras;

public interface IEscriturasRepository extends MongoRepository<Escrituras, String> {
    
}
