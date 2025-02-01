package com.gymapp.converters;

import java.time.LocalDate;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, String>{

    @Override
    public String convertToDatabaseColumn(LocalDate entityValue) {
        if (entityValue == null) {
            return null;
        }
        return entityValue.toString();
    }

    @Override
    public LocalDate convertToEntityAttribute(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        return LocalDate.parse(databaseValue);
    }

}