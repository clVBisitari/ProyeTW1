package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.Usuario;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ProyeInversionDTO {

    private Integer id;
    public String titulo;
    public String description;

    public Usuario titular;
    public Integer inversores;

    public String montoRequerido;
    public String montoRecaudado;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date plazoParaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date plazoParaFinal;

    public Integer cantidadReportes;

    public boolean enSuspension;
    public String motivoSuspension;

    public static ProyeInversionDTO convertToDTO(ProyectoInversion proyectoInversion){

        ProyeInversionDTO proyeInversionDTO = new ProyeInversionDTO();

        proyeInversionDTO.setId(proyectoInversion.getId());
        proyeInversionDTO.setTitulo(proyectoInversion.getTitulo());
        proyeInversionDTO.setDescription(proyectoInversion.getDescription());
        proyeInversionDTO.setTitular(proyectoInversion.getTitular());
        proyeInversionDTO.setMontoRequerido(proyectoInversion.getMontoRequerido());
        proyeInversionDTO.setMontoRecaudado(proyectoInversion.getMontoRecaudado());
        proyeInversionDTO.setPlazoParaFinal(Date.from(proyectoInversion.getPlazoParaFinal().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        proyeInversionDTO.setPlazoParaInicio(Date.from(proyectoInversion.getPlazoParaInicio().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        proyeInversionDTO.setCantidadReportes(proyectoInversion.getCantidadReportes());
        proyeInversionDTO.setEnSuspension(proyectoInversion.isEnSuspension());
        proyeInversionDTO.setMotivoSuspension(proyectoInversion.getMotivoSuspension());

        return proyeInversionDTO;
    }

    public ProyectoInversion convertToProyectoInversion(){

        ProyectoInversion proyectoInversion = new ProyectoInversion();
        proyectoInversion.setId(id);
        proyectoInversion.setTitulo(titulo);
        proyectoInversion.setDescription(description);
        proyectoInversion.setTitular(titular);
        proyectoInversion.setMontoRequerido(new BigDecimal(montoRequerido));

        if (this.montoRecaudado != null) {
            proyectoInversion.setMontoRecaudado(new BigDecimal(montoRecaudado));
        } else {
            proyectoInversion.setMontoRecaudado(new BigDecimal(0));
        }

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
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMontoRequerido(String montoRequerido) {
        this.montoRequerido = montoRequerido;
    }

    public String getMontoRecaudado() {
        return montoRecaudado;
    }

    public void setMontoRecaudado(String montoRecaudado) {
        this.montoRecaudado = montoRecaudado;
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

    public Integer getInversores() {
        return inversores;
    }

    public Integer getId() {
        return id;
    }

    public void setInversores(Integer inversores) {
        this.inversores = inversores;
    }

    public void setMontoRequerido(BigDecimal montoRequerido) {
        this.montoRequerido = montoRequerido.toString();
    }

    public BigDecimal getMontoRequerido() {
        if (montoRequerido == null) return new BigDecimal(0);
        return new BigDecimal(montoRequerido);
    }

    public void setMontoRecaudado(BigDecimal montoRecaudado) {
        this.montoRecaudado = montoRecaudado.toString();
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
