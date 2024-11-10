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

import com.prueba.backend.Model.Valoraciones;
import com.prueba.backend.Service.IValoracionesService;

@RestController
@RequestMapping("/doctic/apiweb/valoraciones")
public class ValoracionesController {
    
    @Autowired
    IValoracionesService valoracionesService;

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarValoracion(@RequestBody Valoraciones valoracion){
        return new ResponseEntity<>(valoracionesService.guardarValoracion(valoracion), HttpStatus.OK);
    }

    @GetMapping("/{_id}")
    public ResponseEntity<?> buscarValoracion(@PathVariable ObjectId _id){
        try {
            Valoraciones valoracion = valoracionesService.buscarValoracion(_id);
            return ResponseEntity.ok(valoracion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{_id}")
    public ResponseEntity<String>actualizarValoracion(@PathVariable ObjectId _id, @RequestBody Valoraciones valoracion){
        try {
            return new ResponseEntity<>(valoracionesService.actualizarValoracion(_id, valoracion), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{_id}")
    public ResponseEntity<String>eliminarValoracion(@PathVariable ObjectId _id, Valoraciones valoracion){
        return new ResponseEntity<>(valoracionesService.eliminarValoracion(_id, valoracion), HttpStatus.OK);
    }
}
