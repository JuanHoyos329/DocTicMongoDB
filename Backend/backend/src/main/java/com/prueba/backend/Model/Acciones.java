package com.prueba.backend.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Acciones {
    
    private List<Descargas> descargas;
    private List<Visualizaciones> visualizaciones;
    private List<Valoraciones> valoraciones;
}
