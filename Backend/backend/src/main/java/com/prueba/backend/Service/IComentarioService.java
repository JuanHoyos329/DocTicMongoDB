package com.prueba.backend.Service;

import org.bson.types.ObjectId;

import com.prueba.backend.Model.ComentariosModel;

public interface IComentarioService {
    
    String guardarComentario(ComentariosModel comentario);
    String eliminarComentario(ObjectId _id, ComentariosModel comentario);
    String actualizarComentario(ObjectId _id, ComentariosModel comentario);
    ComentariosModel buscarComentario(ObjectId _id);
}
