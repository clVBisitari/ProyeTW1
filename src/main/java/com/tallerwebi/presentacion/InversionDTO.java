package com.tallerwebi.presentacion;

import java.math.BigDecimal;
import java.util.Date;

public class InversionDTO {

    public InversionDTO(BigDecimal monto, Integer proyectoId, String titulo) {
        this.monto = monto;
        this.proyectoId = proyectoId;
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Integer getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String titulo;
    public BigDecimal monto;
    public Integer proyectoId;
}
