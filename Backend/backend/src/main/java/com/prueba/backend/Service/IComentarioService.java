package com.prueba.backend.Service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import com.prueba.backend.Model.ComentariosModel;
import com.prueba.backend.Model.Respuestas;

public interface IComentarioService {
    
    String guardarComentario(ComentariosModel comentario);
    String eliminarComentario(ObjectId _id, ComentariosModel comentario);
    String actualizarComentario(ObjectId _id, ComentariosModel comentario);
    ComentariosModel buscarComentario(ObjectId _id);
    List<ComentariosModel> listarComentarios();
    String crearReespuesta(ObjectId _id, Respuestas respuesta);
    String eliminarRespuesta(ObjectId _id, ObjectId idUsuario);
    Optional<Respuestas> obtenerRespuesta(ObjectId _id, ObjectId idUsuario);
}
