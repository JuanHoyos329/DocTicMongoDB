package com.prueba.backend.Service;

import org.bson.types.ObjectId;

import com.prueba.backend.Model.Valoraciones;

public interface IValoracionesService {
        
        String guardarValoracion(Valoraciones valoracion);
        String eliminarValoracion(ObjectId _id, Valoraciones valoracion);
        String actualizarValoracion(ObjectId _id, Valoraciones valoracion);
        Valoraciones buscarValoracion(ObjectId _id);
}
