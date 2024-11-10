package com.prueba.backend.Service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.backend.Model.ComentariosModel;
import com.prueba.backend.Model.DocumentosModel;
import com.prueba.backend.Model.UsuariosModel;
import com.prueba.backend.Repository.IComentariosRepository;
import com.prueba.backend.Repository.IDocumentosRepository;
import com.prueba.backend.Repository.IUsuariosRepository;

@Service
public class ComentarioServicelmp implements IComentarioService {

    @Autowired
    IComentariosRepository comentariosRepository;

    @Autowired
    IDocumentosRepository documentosRepository;

    @Autowired
    IUsuariosRepository usuariosRepository;

    @Override
    public String guardarComentario(ComentariosModel comentario) {
        if (comentario.getIdDocumento() != null && comentario.getIdUsuario() != null) {
            ObjectId idDocumento = comentario.getIdDocumento();
            ObjectId idUsuario = comentario.getIdUsuario();
            DocumentosModel documento = documentosRepository.findById(idDocumento).orElse(null);
            UsuariosModel usuario = usuariosRepository.findById(idUsuario).orElse(null);
            if (documento != null && usuario != null) {
                comentario.setIdDocumento(documento.get_id());
                comentario.setIdUsuario(usuario.get_id());
            } else {
                return "No se ha encontrado un documento o usuario con ese _id.";
            }
        } else {
            return "El id del documento o del usuario no puede ser nulo.";
        }

        if (comentario.getRespuestas() != null) {
            for (ComentariosModel respuesta : comentario.getRespuestas()) {
                if (respuesta.get_id() == null) {
                    return "No se puede crear una respuesta a un comentario con un id nulo.";
                }
            }
        }

        comentariosRepository.save(comentario);
        return "El comentario se ha creado con exito.";
    }

    @Override
    @Transactional
    public String eliminarComentario(ObjectId _id, ComentariosModel comentario) {
        if (comentariosRepository.existsById(_id)) {
            comentariosRepository.deleteById(_id);
            return "El comentario se ha eliminado con exito.";
        } else {
            return "El comentario no se ha encontrado o no existe en la BD.";
        }
    }

    @Override
    public String actualizarComentario(ObjectId _id, ComentariosModel comentario) {
        ComentariosModel comentarioActualizado = buscarComentario(_id);
        if (comentarioActualizado != null) {
            comentarioActualizado.setFecha(comentario.getFecha());
            comentarioActualizado.setContenido(comentario.getContenido());
            comentarioActualizado.setIdUsuario(comentario.getIdUsuario());
            comentarioActualizado.setIdDocumento(comentario.getIdDocumento());
            comentarioActualizado.setRespuestas(comentario.getRespuestas());
            comentariosRepository.save(comentarioActualizado);
            return "El comentario se ha actualizado con exito.";
        } else {
            return "El comentario no se ha encontrado.";
        }
    }

    @Override
    public ComentariosModel buscarComentario(ObjectId _id) {
        return comentariosRepository.findById(_id).orElseThrow
        (() -> new RuntimeException("El comentario no se ha encontrado o no existe en la BD."));
    }
}
