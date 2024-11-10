package com.prueba.backend.Model.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Rol {
    
    Publica, Coautor;

    @JsonCreator
    public static Rol fromString(String key) {
        return key == null ? null : Rol.valueOf(key);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
