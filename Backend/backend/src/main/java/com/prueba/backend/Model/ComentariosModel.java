package com.prueba.backend.Model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("comentarios")
public class ComentariosModel {
    
    @Id
    private ObjectId _id;
    
    private Date fecha;
    private String contenido;
    private ObjectId idUsuario;
    private ObjectId idDocumento;
    private List<Respuestas> respuestas;

    @JsonProperty("_id")
    public String getIdAstring() {
        return _id != null ? _id.toHexString() : null;
    }

    @JsonProperty("idUsuario")
    public String getIdUsuarioAstring() {
        return idUsuario != null ? idUsuario.toHexString() : null;
    }

    @JsonProperty("idDocumento")
    public String getIdDocumentoAstring() {
        return idDocumento != null ? idDocumento.toHexString() : null;
    }

}
