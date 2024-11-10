package com.prueba.backend.Service;

import com.prueba.backend.Model.DocumentosModel;
import com.prueba.backend.Model.Escrituras;

public interface IEscriturasService {

    String crearEscritura(Escrituras escrituras, DocumentosModel documentosModel);
    String eliminarEscritura(Escrituras escrituras, DocumentosModel documentosModel);
    
}
