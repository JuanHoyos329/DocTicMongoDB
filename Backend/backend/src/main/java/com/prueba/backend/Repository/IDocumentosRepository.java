package com.prueba.backend.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.prueba.backend.Model.DocumentosModel;

@Repository
public interface IDocumentosRepository extends MongoRepository<DocumentosModel, ObjectId>{

    @Query(value = "{ '_id': ?1, 'acciones.descargas.idUsuario': ?0 }", exists = true)
    boolean existsByUsuarioDescarga(ObjectId idUsuario, ObjectId idDocumento);

    @Query(value = "{ '_id': ?1, 'acciones.visualizaciones.idUsuario': ?0 }", exists = true)
    boolean existsByUsuarioVisualizacion(ObjectId idUsuario, ObjectId idDocumento);

}
