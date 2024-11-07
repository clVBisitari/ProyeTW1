package com.tallerwebi.dominio;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PROYECTOINVERSION")
public class ProyectoInversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String description;
    @OneToOne
    @JoinColumn(name = "Usuario")
    private Usuario titular;
    @ManyToMany
    @JoinTable(name = "inversores")
    @JoinColumn(name = "inversor_id")
    private List<Usuario> inversores;
    private Integer montoRequerido;
    private Integer montoRecaudado;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date plazoParaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date plazoParaFinal;
    private Integer cantidadReportes;
    private boolean enSuspension;
    private String motivoSuspension;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public Date getPlazoParaInicio() {
        return plazoParaInicio;
    }

    public void setPlazoParaInicio(Date plazoParaInicio) {
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

    public Date getPlazoParaFinal() {
        return plazoParaFinal;
    }

    public void setPlazoParaFinal(Date plazoParaFinal) {
        this.plazoParaFinal = plazoParaFinal;
    }

    public Integer getCantidadReportes() {
        return cantidadReportes;
    }

    public void setCantidadReportes(Integer cantidadReportes) {
        this.cantidadReportes = cantidadReportes;
    }
}
