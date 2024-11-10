package com.prueba.backend.Service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.backend.Model.Valoraciones;
import com.prueba.backend.Repository.IValoracionesRepository;

@Service
public class ValoracionesServicelmp implements IValoracionesService {
    @Autowired
    IValoracionesRepository valoracionesRepository;
    
    @Override
    public String guardarValoracion(Valoraciones valoracion) {
        valoracionesRepository.save(valoracion);
        return "La valoracion se a dado con exito.";
    }

    @Override
    @Transactional
    public String eliminarValoracion(ObjectId _id, Valoraciones valoracion) {
        if (valoracionesRepository.existsById(_id)){
            valoracionesRepository.deleteById(_id);
            return "La valoracion se ha eliminado con exito";
        } else {
            return "La valoracion no se ha encontrado o no existe en la BD.";
        }
    }

    @Override
    public String actualizarValoracion(ObjectId _id, Valoraciones valoracion) {
        Valoraciones valoracionActualizada = buscarValoracion(_id);
        if (valoracionesRepository.existsById(_id)){
            valoracionActualizada.getPuntuacion();
            valoracionesRepository.save(valoracion);
            return "La valoracion se ha actualizado con exito.";
        } else {
            return "No has valorado este documento.";
        }
    }

    @Override
    public Valoraciones buscarValoracion(ObjectId _id) {
        return valoracionesRepository.findById(_id).orElseThrow(() -> new RuntimeException("No has valorado este documento."));
    }
    
}
