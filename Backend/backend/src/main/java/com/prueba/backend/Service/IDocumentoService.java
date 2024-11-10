package com.prueba.backend.Service;

import java.util.Optional;

import org.bson.types.ObjectId;

import com.prueba.backend.Model.DocumentosModel;
import com.prueba.backend.Model.Escrituras;

public interface IDocumentoService {
    
    String guardarDocumento(DocumentosModel documento, Escrituras escritura);
    String eliminarDocumento(ObjectId _id, DocumentosModel documento);
    String actualizarDocumento(ObjectId _id, DocumentosModel documento);
    Optional<DocumentosModel> buscarDocumento(ObjectId _id);
}
