package com.prueba.backend.Model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor 
@NoArgsConstructor
@Document
public class Respuestas {
    private ObjectId idUsuario;
    private Date fecha;
    private String contenido;

    @JsonProperty("idUsuario")
    public String getIdUsuario() {
        return idUsuario != null ? idUsuario.toHexString() : null;
    }
}
