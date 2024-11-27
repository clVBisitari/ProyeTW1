package com.tallerwebi.presentacion;

public class InversorDeProyectoDTO {
    public Integer idProyecto;
    public String monto;
    public String idUsuario;

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getIdUsuario() {
        return idUsuario;
    }
}
