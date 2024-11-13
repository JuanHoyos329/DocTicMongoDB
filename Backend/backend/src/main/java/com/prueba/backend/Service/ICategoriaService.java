package com.prueba.backend.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.prueba.backend.Model.CategoriasModel;

public interface ICategoriaService {
    
    String guardarCategoria(CategoriasModel categoria);
    String eliminarCategoria(ObjectId _id, CategoriasModel categoria);
    String actualizarCategoria(ObjectId _id, CategoriasModel categoria);
    CategoriasModel buscarCategoria(ObjectId _id);
    List<CategoriasModel> listarCategorias();
}
