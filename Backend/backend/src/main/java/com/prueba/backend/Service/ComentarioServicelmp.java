package com.prueba.backend.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.result.UpdateResult;
import com.prueba.backend.Model.ComentariosModel;
import com.prueba.backend.Model.Respuestas;
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

    @Autowired
    private MongoTemplate mongoTemplate;

    
    @Override
    public String guardarComentario(ComentariosModel comentario) {
        ObjectId idDocumento = comentario.getIdDocumento();
        ObjectId idUsuario = comentario.getIdUsuario();

        boolean haDescargado = documentosRepository.existsByUsuarioDescarga(idUsuario, idDocumento);

        if (!usuariosRepository.existsById(idUsuario)) {
            return "El usuario no existe.";
        }

        if (!documentosRepository.existsById(idDocumento)) {
            return "El documento no existe.";
        }
        
        if (!haDescargado) {
            return "El usuario no ha descargado el documento.";
        }

        boolean haVisto = documentosRepository.existsByUsuarioVisualizacion(idUsuario, idDocumento);
        if (!haVisto) {
            return "El usuario no ha visualizado el documento.";
        }

        comentario.setFecha(new Date());
        comentariosRepository.save(comentario);
        return "El comentario se ha guardado con éxito.";
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
    public String actualizarComentario(ObjectId idComentario, ComentariosModel comentario) {
        Optional<ComentariosModel> comentarioExistente = comentariosRepository.findById(idComentario);
    
        if (comentarioExistente.isPresent()) {
            ComentariosModel comentarioGuardado = comentarioExistente.get();
            
            if (comentario.getContenido() != null) {
                comentarioGuardado.setContenido(comentario.getContenido());
            }
    
            if (comentario.getRespuestas() != null) {
                comentarioGuardado.setRespuestas(comentario.getRespuestas());
            }
    
            comentariosRepository.save(comentarioGuardado);
            return "Comentario actualizado exitosamente.";
        } else {
            return "Comentario no encontrado.";
        }
    }
    
    @Override
    public ComentariosModel buscarComentario(ObjectId _id) {
        return comentariosRepository.findById(_id).orElseThrow
        (() -> new RuntimeException("El comentario no se ha encontrado o no existe en la BD."));
    }

    @Override
    public List<ComentariosModel> listarComentarios() {
        return comentariosRepository.findAll();
    }


    @Override
    public String crearReespuesta(ObjectId _id, Respuestas respuesta) {
        ComentariosModel comentario = buscarComentario(_id);

        if (comentario != null) {
            respuesta.setFecha(new Date());

            if (comentario.getRespuestas() == null) {
                comentario.setRespuestas(new ArrayList<>());
            }

            comentario.getRespuestas().add(respuesta);
            comentariosRepository.save(comentario);
            return "La respuesta se ha creado con éxito.";
        } else {
            return "El comentario no se ha encontrado.";
        }
    }


    @Override
    public String eliminarRespuesta(ObjectId _id, ObjectId idUsuario) {
        Update update = new Update().pull("respuestas", Query.query(Criteria.where("idUsuario").is(idUsuario)));
        UpdateResult result = mongoTemplate.updateFirst(
            Query.query(Criteria.where("_id").is(_id)),
            update,
            ComentariosModel.class
        );

        if (result.getModifiedCount() > 0) {
            return "La respuesta se ha eliminado con éxito.";
        } else {
            return "No se encontró una respuesta del usuario especificado en el comentario.";
        }
    }

    @Override
    public Optional<Respuestas> obtenerRespuesta(ObjectId comentarioId, ObjectId idUsuario) {
        Query query = new Query(Criteria.where("_id").is(comentarioId)
                .and("respuestas.idUsuario").is(idUsuario));
    
        query.fields().include("respuestas.$");
    
        ComentariosModel comentario = mongoTemplate.findOne(query, ComentariosModel.class);
    
        if (comentario != null && comentario.getRespuestas() != null && !comentario.getRespuestas().isEmpty()) {
            return Optional.of(comentario.getRespuestas().get(0));
        } else {
            return Optional.empty();
        }
    }         
} 