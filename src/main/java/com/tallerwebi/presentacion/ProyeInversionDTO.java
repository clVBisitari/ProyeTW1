package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.Usuario;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ProyeInversionDTO {
    private Integer id;
    public String titulo;
    public String description;
    public Usuario titular;
    public List<Usuario> inversores;
    public Integer montoRequerido;
    public Integer montoRecaudado;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date plazoParaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date plazoParaFinal;
    public Integer cantidadReportes;
    public boolean enSuspension;
    public String motivoSuspension;

    public ProyectoInversion convertToProyectoInversion(){
        ProyectoInversion proyectoInversion = new ProyectoInversion();
        proyectoInversion.setId(id);
        proyectoInversion.setTitulo(titulo);
        proyectoInversion.setDescription(description);
        proyectoInversion.setTitular(titular);
        proyectoInversion.setMontoRequerido(montoRequerido);
        proyectoInversion.setMontoRecaudado(montoRecaudado);

        // Convierte `Date` a `LocalDate`
        if (plazoParaInicio != null) {
            proyectoInversion.setPlazoParaInicio(convertToLocalDate(plazoParaInicio));
        }
        if (plazoParaFinal != null) {
            proyectoInversion.setPlazoParaFinal(convertToLocalDate(plazoParaFinal));
        }
        proyectoInversion.setCantidadReportes(cantidadReportes);
        proyectoInversion.setEnSuspension(enSuspension);
        proyectoInversion.setMotivoSuspension(motivoSuspension);
        return proyectoInversion;
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        LocalDate fechaConvertida = dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return fechaConvertida;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getTitulo(){
        return titulo;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription(){
        return description;
    }

    public void setTitular(Usuario titular) {
        this.titular = titular;
    }
    public Usuario getTitular(){
        return titular;
    }

    public List<Usuario> getInversores() {
        return inversores;
    }
    public Integer getId() {
        return id;
    }

    public void setInversores(List<Usuario> inversores) {
        this.inversores = inversores;
    }

    public void setMontoRequerido(Integer montoRequerido) {
        this.montoRequerido = montoRequerido;
    }
    public Integer getMontoRequerido() {
        return montoRequerido;
    }

    public void setMontoRecaudado(Integer montoRecaudado) {
        this.montoRecaudado = montoRecaudado;
    }

    public void setPlazoParaInicio(Date plazoParaInicio) {
        this.plazoParaInicio = plazoParaInicio;
    }
    public Date getPlazoParaInicio() {
        return plazoParaInicio;
    }

    public void setPlazoParaFinal(Date plazoParaFinal) {
        this.plazoParaFinal = plazoParaFinal;
    }

    public Date getPlazoParaFinal() {
        return plazoParaFinal;
    }
    public Integer getCantidadReportes() {
        return cantidadReportes;
    }
    public void setCantidadReportes(Integer cantidadReportes) {
        this.cantidadReportes = cantidadReportes;
    }

    public void setEnSuspension(boolean enSuspension) {
        this.enSuspension = enSuspension;
    }
    public boolean isEnSuspension() {
        return enSuspension;
    }
    public String getMotivoSuspension() {
        return motivoSuspension;
    }
    public void setMotivoSuspension(String motivoSuspension) {
        this.motivoSuspension = motivoSuspension;
    }
}