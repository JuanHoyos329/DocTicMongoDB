package com.prueba.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prueba.backend.Model.DocumentosModel;
import com.prueba.backend.Model.Escrituras;
import com.prueba.backend.Service.IDocumentoService;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctic/apiweb/documentos")
public class DocumentosController {

    @Autowired
    private IDocumentoService documentoService;

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarDocumento(@RequestBody DocumentosModel documento, Escrituras escritura) {
        try {
            return new ResponseEntity<>(documentoService.guardarDocumento(documento, escritura), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("{_id}")
    public ResponseEntity<?> buscarDocumento(@PathVariable ObjectId _id) {
        try {
            Optional<DocumentosModel> documento = documentoService.buscarDocumento(_id);
            return ResponseEntity.ok(documento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{_id}/usuario/{idUsuario}")
    public ResponseEntity<?> buscarEscritores(@PathVariable ObjectId _id, @PathVariable ObjectId idUsuario) {
        try {
            Optional<List<Escrituras>> escritores = documentoService.buscarEscritores(_id, idUsuario);

            if (escritores.isPresent() && !escritores.get().isEmpty()) {
                return ResponseEntity.ok(escritores.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron escritores para este documento.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listar/documentos")
    public ResponseEntity<?> listarDocumentos() {
        return new ResponseEntity<>(documentoService.listarDocumentos(), HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarDocumento(
            @PathVariable("id") ObjectId id, 
            @RequestBody DocumentosModel documento) {

            String resultado = documentoService.actualizarDocumento(id, documento);
            if (resultado.startsWith("Error")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
            }
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/eliminar/{_id}")
    public ResponseEntity<String>eliminarDocumento(@PathVariable ("_id") ObjectId _id, DocumentosModel documento){
        return new ResponseEntity<>(documentoService.eliminarDocumento(_id, documento), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{_id}/escritor/{idUsuario}")
    public ResponseEntity<String> eliminarEscritor(@PathVariable("_id") ObjectId _id, @PathVariable("idUsuario") ObjectId idUsuario, DocumentosModel documento) {
        String resultado = documentoService.eliminarEscritores(_id, idUsuario, documento);
        if (resultado.startsWith("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }
}
