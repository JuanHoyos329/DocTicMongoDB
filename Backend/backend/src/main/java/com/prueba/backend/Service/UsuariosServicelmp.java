package com.prueba.backend.Service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.backend.Model.UsuariosModel;
import com.prueba.backend.Repository.IUsuariosRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServicelmp implements IUsuariosService {

    @Autowired
    IUsuariosRepository usuariosRepository;


    @Override
    public String guardarUsuario(UsuariosModel usuario) {
        usuariosRepository.save(usuario);
        return "El usuario " + usuario.getNickname() + " se a creado con exito.";
    }

    @Override
    @Transactional
    public String eliminarUsuario(ObjectId _id, UsuariosModel usuario) {
        Optional<UsuariosModel> usuarioOptional = usuariosRepository.findById(_id);
        if (usuarioOptional.isPresent()) {
            usuario = usuarioOptional.get();
            usuariosRepository.deleteById(_id);
            return "El usuario " + usuario.getNickname() + " se ha eliminado con exito";
        } else {
            return "El usuario " + usuario.getNickname()+ " no se ha encontrado o no existe en la BD.";
        }
    }

    @Override
    public String actualizarUsuario(ObjectId _id, UsuariosModel usuario) {
        UsuariosModel usuarioActualizado = buscarUsuario(_id);
        if (usuarioActualizado != null) {
            usuarioActualizado.setNombre(usuario.getNombre());
            usuarioActualizado.setEmail(usuario.getEmail());
            usuarioActualizado.setNickname(usuario.getNickname());
            usuarioActualizado.setCiudad(usuario.getCiudad());
            usuarioActualizado.setDepartamento(usuario.getDepartamento());
            usuarioActualizado.setContrasena(usuario.getContrasena());
            usuariosRepository.save(usuarioActualizado);

            return "El usuario " + usuario.getNickname() + " se ha actualizado con exito." ;
        }
        else {
            return "El usuario " + usuario.getNickname() + " no se encuentra o no exite en la BD.";
        }
    }

    @Override
    public UsuariosModel buscarUsuario(ObjectId _id) {
        return usuariosRepository.findById(_id).orElseThrow(() -> new RuntimeException("El usuario no se ha encontrado o no existe en la BD."));
    }

    @Override
    public List<UsuariosModel> listarUsuarios() {
        return usuariosRepository.findAll();
    }

}
