package com.prueba.backend.Model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.prueba.backend.Model.Enum.Estado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ContrasenasUsuarios {
    
    private String clave;
    private Estado estado;
    private Date fechaCreacion;
    private String preguntaSecreta;
    private String respuestaSecreta;
}
