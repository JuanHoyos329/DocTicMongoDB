package com.prueba.backend.Model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prueba.backend.Model.Enum.Rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Escrituras {
    
    private Date fecha;
    private Rol rol;
    private ObjectId idUsuario;

    @JsonProperty("idUsuario")
    public String getIdUsuarioString() {
        return idUsuario != null ? idUsuario.toHexString() : null;
    }
}
