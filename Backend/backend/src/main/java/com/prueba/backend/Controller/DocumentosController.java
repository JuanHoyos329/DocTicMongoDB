package com.prueba.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prueba.backend.Model.DocumentosModel;
import com.prueba.backend.Model.Escrituras;
import com.prueba.backend.Service.IDocumentoService;

import org.bson.types.ObjectId;

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

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarDocumento(
            @PathVariable("id") ObjectId id, 
            @RequestBody DocumentosModel documento) {

        // Llamada al servicio para actualizar el documento
        String resultado = documentoService.actualizarDocumento(id, documento);
        // Verificar el resultado para decidir la respuesta HTTP adecuada
        if (resultado.startsWith("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/eliminar/{_id}")
    public ResponseEntity<String>eliminarDocumento(@PathVariable ("_id") ObjectId _id, DocumentosModel documento){
        return new ResponseEntity<>(documentoService.eliminarDocumento(_id, documento), HttpStatus.OK);
    }

}