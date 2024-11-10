package com.prueba.backend.Controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.backend.Model.ComentariosModel;
import com.prueba.backend.Service.IComentarioService;

@RestController
@RequestMapping("/doctic/apiweb/comentarios")
public class ComentarioController {
    
    @Autowired
    IComentarioService comentarioService;

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarComentario(@RequestBody ComentariosModel comentario){
        return new ResponseEntity<>(comentarioService.guardarComentario(comentario), HttpStatus.OK);
    }

    @GetMapping("/{_id}")
    public ResponseEntity<?> buscarComentario(@PathVariable ObjectId _id){
        try {
            ComentariosModel comentario = comentarioService.buscarComentario(_id);
            return ResponseEntity.ok(comentario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PutMapping("/actualizar/{_id}")
    public ResponseEntity<String>actualizarComentario(@PathVariable ObjectId _id, @RequestBody ComentariosModel comentario){
        try {
            return new ResponseEntity<>(comentarioService.actualizarComentario(_id, comentario), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{_id}")
    public ResponseEntity<String>eliminarComentario(@PathVariable ObjectId _id, ComentariosModel comentario){
        return new ResponseEntity<>(comentarioService.eliminarComentario(_id, comentario), HttpStatus.OK);
    }
}
