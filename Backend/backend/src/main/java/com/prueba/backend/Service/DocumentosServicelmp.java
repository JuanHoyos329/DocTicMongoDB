package com.prueba.backend.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.backend.Model.DocumentosModel;
import com.prueba.backend.Model.Escrituras;
import com.prueba.backend.Model.Enum.Rol;
import com.prueba.backend.Repository.IDocumentosRepository;
import com.prueba.backend.Repository.ICategoriasRepository;
import com.prueba.backend.Repository.IUsuariosRepository;

@Service
public class DocumentosServicelmp implements IDocumentoService {

    @Autowired
    IDocumentosRepository documentosRepository;

    @Autowired
    IUsuariosRepository usuariosRepository;

    @Autowired
    ICategoriasRepository categoriasRepository;
    
    @Override
    public String guardarDocumento(DocumentosModel documento, Escrituras escritura) {
        boolean categoriaExiste = categoriasRepository.existsById(documento.getIdCategoria());
        boolean usuarioExiste = usuariosRepository.existsById(documento.getEscritura().get(0).getIdUsuario());
        boolean rolCorrecto = documento.getEscritura().stream().allMatch(e -> e.getRol() == Rol.Publica);
        
        if (categoriaExiste && usuarioExiste && rolCorrecto) {
            documentosRepository.save(documento);
            return "El documento " + documento.getDescripcion() + " se ha guardado correctamente.";
        } else {
            if (!categoriaExiste) {
                return "La categoría no existe.";
            }
            if (!usuarioExiste) {
                return "El usuario no existe.";
            }
            if (!rolCorrecto) {
                return "El rol no puede ser diferente a Publica.";
            }
            return "La información del documento no es correcta.";
        }
    }

    @Override
    @Transactional
    public String eliminarDocumento(ObjectId _id, DocumentosModel documento) {
        Optional<DocumentosModel> documentoOptional = documentosRepository.findById(_id);

        if (documentoOptional.isPresent()) {
            documento = documentoOptional.get();
            documentosRepository.deleteById(_id);
            return "El documento " + documento.getDescripcion() + " fue eliminado con exito";
        } else {
            return "El documento no se ha encontrado o no existe en la BD.";
        }
    }

    @Override
    public String eliminarEscritores(ObjectId _id, ObjectId idUsuario, DocumentosModel documento) {
        Optional<DocumentosModel> documentoOptional = documentosRepository.findById(_id);
        
        if (!documentoOptional.isPresent()) {
            return "El documento no se ha encontrado o no existe en la BD.";
        }
        
        documento = documentoOptional.get();
        List<Escrituras> escrituras = documento.getEscritura();
        
        boolean rolCorrecto = escrituras.stream().anyMatch(e -> e.getRol() == Rol.Publica && e.getIdUsuario().equals(idUsuario));
        
        if (!rolCorrecto) {
            escrituras.removeIf(e -> e.getIdUsuario().equals(idUsuario));
            documento.setEscritura(escrituras);
            documentosRepository.save(documento);
            return "El escritor fue eliminado con éxito";
        } else {
            return "El autor con el rol público no puede ser eliminado del documento.";
        }
    }

    @Override
    public String actualizarDocumento(ObjectId _id, DocumentosModel documento) {
        Optional<DocumentosModel> documentoActualizado = buscarDocumento(_id);

        if (!documentoActualizado.isPresent()) {
            return "El documento no se ha encontrado o no existe en la BD.";
        }

        DocumentosModel doc = documentoActualizado.get();

        String error = validarDatos(documento, doc);
        if (error != null) {
            return error;
        }

        actualizarEscritura(documento, doc);

        actualizarDocumento(documento, doc);
        documentosRepository.save(doc);

        return "El documento " + doc.getDescripcion() + " se ha actualizado correctamente.";
    }

    private String validarDatos(DocumentosModel nuevoDocumento, DocumentosModel documentoExistente) {
        if (nuevoDocumento.getIdCategoria() != null && !categoriasRepository.existsById(nuevoDocumento.getIdCategoria())) {
            return "La categoría no existe.";
        }

        if (!nuevoDocumento.getEscritura().isEmpty() &&
            !usuariosRepository.existsById(nuevoDocumento.getEscritura().get(0).getIdUsuario())) {
            return "El usuario no existe.";
        }

        boolean tieneRolPublicaActual = documentoExistente.getEscritura().stream()
                .anyMatch(e -> e.getRol() == Rol.Publica);
        boolean nuevoRolPublica = nuevoDocumento.getEscritura().stream()
                .anyMatch(e -> e.getRol() == Rol.Publica);

        if (tieneRolPublicaActual && nuevoRolPublica && !verificarPublica(nuevoDocumento, documentoExistente)) {
            return "Solo puede existir un usuario con el rol Publica.";
        }
        return null;
    }

    private boolean verificarPublica(DocumentosModel nuevoDocumento, DocumentosModel documentoExistente) {
        Escrituras usuarioActualPublica = documentoExistente.getEscritura().stream()
                .filter(e -> e.getRol() == Rol.Publica)
                .findFirst().orElse(null);

        return nuevoDocumento.getEscritura().stream()
                .anyMatch(e -> e.getRol() == Rol.Publica && e.getIdUsuario().equals(usuarioActualPublica.getIdUsuario()));
    }

    private void actualizarEscritura(DocumentosModel nuevoDocumento, DocumentosModel documentoExistente) {
        if (!nuevoDocumento.getEscritura().isEmpty()) {
            List<Escrituras> nuevaEscritura = new ArrayList<>(documentoExistente.getEscritura());
            nuevaEscritura.addAll(nuevoDocumento.getEscritura());
            documentoExistente.setEscritura(new ArrayList<>(new HashSet<>(nuevaEscritura)));
        }
    }

    private void actualizarDocumento(DocumentosModel nuevoDocumento, DocumentosModel documentoExistente) {
        Optional.ofNullable(nuevoDocumento.getDescripcion()).ifPresent(documentoExistente::setDescripcion);
        Optional.ofNullable(nuevoDocumento.getUrl()).ifPresent(documentoExistente::setUrl);
        Optional.ofNullable(nuevoDocumento.getVisibilidad()).ifPresent(documentoExistente::setVisibilidad);
        Optional.ofNullable(nuevoDocumento.getFechaCreacion()).ifPresent(documentoExistente::setFechaCreacion);
        Optional.ofNullable(nuevoDocumento.getAcciones()).ifPresent(documentoExistente::setAcciones);
        Optional.ofNullable(nuevoDocumento.getIdCategoria()).ifPresent(documentoExistente::setIdCategoria);
    }

    @Override
    public Optional<DocumentosModel> buscarDocumento(ObjectId _id) {
        return documentosRepository.findById(_id);
    }

    @Override
    public Optional<List<Escrituras>> buscarEscritores(ObjectId _id, ObjectId idUsuario) {
        return documentosRepository.findById(_id)
            .map(doc -> doc.getEscritura());
    }


}
