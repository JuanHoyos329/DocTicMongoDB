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

import com.prueba.backend.Model.UsuariosModel;
import com.prueba.backend.Service.IUsuariosService;

@RestController
@RequestMapping("/doctic/apiweb/usuarios")
public class UsuariosController {
    
    @Autowired
    IUsuariosService usuariosService;

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarUsuario(@RequestBody UsuariosModel usuario){
        return new ResponseEntity<>(usuariosService.guardarUsuario(usuario), HttpStatus.OK);
    }

    @GetMapping("/{_id}")
    public ResponseEntity<?> buscarUsuario(@PathVariable ObjectId _id){
        try {
            UsuariosModel usuario = usuariosService.buscarUsuario(_id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{_id}")
    public ResponseEntity<String>actualizarUsuario(@PathVariable ObjectId _id, @RequestBody UsuariosModel usuario){
        try {
            return new ResponseEntity<>(usuariosService.actualizarUsuario(_id, usuario), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{_id}")
    public ResponseEntity<String>eliminarUsuario(@PathVariable("_id") ObjectId _id, UsuariosModel usuario){
        return new ResponseEntity<>(usuariosService.eliminarUsuario(_id, usuario), HttpStatus.OK);
    } 
}
