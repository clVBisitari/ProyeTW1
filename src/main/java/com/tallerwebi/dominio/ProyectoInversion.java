package com.tallerwebi.dominio;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "proyecto_inversion")
public class ProyectoInversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String description;
    @ManyToOne
    @JoinColumn(name = "titular_id")
    private Usuario titular;
    @ManyToMany
    @JoinTable(name = "inversores")
    @JoinColumn(name = "inversor_id")
    private List<Usuario> inversores;
    private Integer montoRequerido;
    private Integer montoRecaudado;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate plazoParaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate plazoParaFinal;
    private Integer cantidadReportes;
    private boolean enSuspension;
    private String motivoSuspension;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Usuario getTitular() {
        return titular;
    }

    public void setTitular(Usuario titular) {
        this.titular = titular;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Usuario> getInversores() {
        return inversores;
    }

    public void setInversores(List<Usuario> inversores) {
        this.inversores = inversores;
    }

    public Integer getMontoRequerido() {
        return montoRequerido;
    }

    public void setMontoRequerido(Integer montoRequerido) {
        this.montoRequerido = montoRequerido;
    }

    public Integer getMontoRecaudado() {
        return montoRecaudado;
    }

    public void setMontoRecaudado(Integer montoRecaudado) {
        this.montoRecaudado = montoRecaudado;
    }

    public LocalDate getPlazoParaInicio() {
        return plazoParaInicio;
    }

    public void setPlazoParaInicio(LocalDate plazoParaInicio) {
        this.plazoParaInicio = plazoParaInicio;
    }

    public boolean isEnSuspension() {
        return enSuspension;
    }

    public void setEnSuspension(boolean enSuspension) {
        this.enSuspension = enSuspension;
    }

    public String getMotivoSuspension() {
        return motivoSuspension;
    }

    public void setMotivoSuspension(String motivoSuspension) {
        this.motivoSuspension = motivoSuspension;
    }

    public LocalDate getPlazoParaFinal() {
        return plazoParaFinal;
    }

    public void setPlazoParaFinal(LocalDate plazoParaFinal) {
        this.plazoParaFinal = plazoParaFinal;
    }

    public Integer getCantidadReportes() {
        return cantidadReportes;
    }

    public void setCantidadReportes(Integer cantidadReportes) {
        this.cantidadReportes = cantidadReportes;
    }
}
