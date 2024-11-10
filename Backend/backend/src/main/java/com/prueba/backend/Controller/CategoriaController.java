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

import com.prueba.backend.Model.CategoriasModel;
import com.prueba.backend.Service.ICategoriaService;

@RestController
@RequestMapping("/doctic/apiweb/categorias")
public class CategoriaController {
    
    @Autowired
    ICategoriaService categoriaService;

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarCategoria(@RequestBody CategoriasModel categoria){
        return new ResponseEntity<>(categoriaService.guardarCategoria(categoria), HttpStatus.OK);
    }

    @GetMapping("/{_id}")
    public ResponseEntity<?> buscarCategoria(@PathVariable ObjectId _id){
        try {
            CategoriasModel categoria = categoriaService.buscarCategoria(_id);
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{_id}")
    public ResponseEntity<String>actualizarCategoria(@PathVariable ObjectId _id, @RequestBody CategoriasModel categoria){
        try {
            return new ResponseEntity<>(categoriaService.actualizarCategoria(_id, categoria), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{_id}")
    public ResponseEntity<String>eliminarCategoria(@PathVariable ObjectId _id, CategoriasModel categoria){
        return new ResponseEntity<>(categoriaService.eliminarCategoria(_id, categoria), HttpStatus.OK);
    }
}
