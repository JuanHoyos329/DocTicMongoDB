package com.prueba.backend.Model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("categorias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriasModel {
    
    @Id
    private ObjectId _id;
    
    private String nombre;
    private ObjectId subIdCategoria;

    @JsonProperty("_id")
    public String getIdAstring() {
        return _id != null ? _id.toHexString() : null;
    }

    @JsonProperty("subIdCategoria")
    public String getSubIdCategoriaAstring() {
        return subIdCategoria != null ? subIdCategoria.toHexString() : null;
    }
}
