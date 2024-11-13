package com.prueba.backend.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.prueba.backend.Model.UsuariosModel;

public interface IUsuariosService {
    
    String guardarUsuario(UsuariosModel usuario);
    String eliminarUsuario(ObjectId _id, UsuariosModel usuario);
    String actualizarUsuario(ObjectId _id, UsuariosModel usuario);
    UsuariosModel buscarUsuario(ObjectId _id);
    List<UsuariosModel> listarUsuarios();
}
