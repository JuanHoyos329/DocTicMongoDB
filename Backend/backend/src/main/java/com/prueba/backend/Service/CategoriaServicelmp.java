package com.prueba.backend.Service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.backend.Model.CategoriasModel;
import com.prueba.backend.Repository.ICategoriasRepository;

@Service
public class CategoriaServicelmp implements ICategoriaService {

    @Autowired
    ICategoriasRepository categoriasRepository;

    @Override
    public String guardarCategoria(CategoriasModel categoria) {
        if (categoria.getSubIdCategoria() != null) {
            ObjectId subId = categoria.getSubIdCategoria();
            CategoriasModel subCategoria = categoriasRepository.findById(subId).orElse(null);
            if (subCategoria != null) {
                categoria.setSubIdCategoria(subId);
            } else {
                return "No se ha encontrado una categoria con ese _id.";
            }
        }
        categoriasRepository.save(categoria);
        return "La categoria " + categoria.getNombre() + " se ha creado con exito.";
    }

    @Override
    @Transactional
    public String eliminarCategoria(ObjectId _id, CategoriasModel categoria) {
        if (categoriasRepository.existsById(_id)) {
            categoriasRepository.deleteById(_id);
            return "La categoria se ha eliminado con exito";
        } else {
            return "La categoria no se ha encontrado o no existe en la BD.";
        }
    }

    @Override
    public String actualizarCategoria(ObjectId _id, CategoriasModel categoria) {
        CategoriasModel categoriaActualizado = buscarCategoria(_id);
        if (categoriaActualizado != null) {
            categoriaActualizado.setNombre(categoria.getNombre());
            if (categoria.getSubIdCategoria() != null) {
                ObjectId subId = categoria.getSubIdCategoria();
                CategoriasModel subCategoria = categoriasRepository.findById(subId).orElse(null);
                if (subCategoria != null) {
                    categoriaActualizado.setSubIdCategoria(subId);
                } else {
                    return "No se ha encontrado una categoria con ese _id.";
                }
            } else {
                categoriaActualizado.setSubIdCategoria(null);
            }
            categoriasRepository.save(categoriaActualizado);
            return "La categoria " + categoriaActualizado.getNombre() + " se ha actualizado con exito.";
        } else {
            return "La categoria con id " + _id.toHexString() + " no se ha encontrado.";
        }
    }

    @Override
    public CategoriasModel buscarCategoria(ObjectId _id) {
        return categoriasRepository.findById(_id).orElseThrow(() -> new RuntimeException("La categoria no se ha encontrado o no existe en la BD."));
    }
}
