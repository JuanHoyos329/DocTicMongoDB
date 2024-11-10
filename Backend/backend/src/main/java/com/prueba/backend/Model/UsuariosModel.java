package com.prueba.backend.Model;

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
@Document("usuarios")
public class UsuariosModel {
    
    @Id
    private ObjectId _id;
    
    private String nombre;
    private String email;
    private String nickname;
    private String ciudad;
    private String departamento;
    private List<ContrasenasUsuarios> contrasena;
    
    @JsonProperty("_id")
    public String getIdAstring() {
        return _id != null ? _id.toHexString() : null;
    }

    public UsuariosModel(ObjectId _id){
        this._id = _id;
    }
}
