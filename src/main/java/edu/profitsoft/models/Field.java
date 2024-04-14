package edu.profitsoft.models;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Field {
    NAME("name", false),
    ARTIST("artist", false),
    ALBUM("album", false),
    YEAR("year", false),
    GENRES("genres", true);

    final String fieldName;
    final boolean isCollection;

    Field(String fieldName, boolean isCollection) {
        this.fieldName = fieldName;
        this.isCollection = isCollection;
    }

    public static final Map<String, Field> namesMap = new HashMap<>();

    static {
        for (Field field : Field.values()) {
            namesMap.put(field.getFieldName(), field);
        }
    }
}
