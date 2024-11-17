package com.tallerwebi.dominio.helpers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class FechaHelper {

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        LocalDate fechaConvertida = dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return fechaConvertida;
    }
}
