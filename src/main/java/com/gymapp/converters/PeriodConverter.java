package com.gymapp.converters;

import java.time.Period;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PeriodConverter implements AttributeConverter<Period, String> {
   
    @Override
    public String convertToDatabaseColumn(Period entityValue) {
        if (entityValue == null) {
            return null;
        }
        return entityValue.toString();
    }

    @Override
    public Period convertToEntityAttribute(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        return Period.parse(databaseValue);
    }

}