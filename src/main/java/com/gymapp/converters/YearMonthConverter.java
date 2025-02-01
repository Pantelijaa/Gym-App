package com.gymapp.converters;

import java.time.YearMonth;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class YearMonthConverter implements AttributeConverter<YearMonth, String> {

   @Override
    public String convertToDatabaseColumn(YearMonth entityValue) {
        if (entityValue == null) {
            return null;
        }
        return entityValue.toString();
    }

    @Override
    public YearMonth convertToEntityAttribute(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        return YearMonth.parse(databaseValue);
    } 

}