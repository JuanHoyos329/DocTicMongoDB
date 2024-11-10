package com.prueba.backend.Model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prueba.backend.Model.Enum.Visibilidad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("documentos")
public class DocumentosModel {
    
    @Id
    private ObjectId _id;
    
    private String descripcion;
    private String url;
    private Visibilidad visibilidad;
    private Date fechaCreacion;
    private List<Escrituras> escritura;
    private ObjectId idCategoria;
    private Acciones acciones;
    private Valoraciones valoraciones;

    @JsonProperty("_id")
    public String getIdAstring() {
        return _id != null ? _id.toHexString() : null;
    }

    @JsonProperty("idCategoria")
    public String getIdCategoriaAstring() {
        return idCategoria != null ? idCategoria.toHexString() : null;
    }
}