package com.tallerwebi.dominio;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    @OneToOne
    private InversorDeProyecto inversor;
    private BigDecimal montoRequerido;
    private BigDecimal montoRecaudado;
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

    public InversorDeProyecto getInversores() {
        return inversor;
    }

    public void setInversores(InversorDeProyecto inversores) {
        this.inversor = inversores;
    }

    public BigDecimal getMontoRequerido() {
        return montoRequerido;
    }

    public void setMontoRequerido(BigDecimal montoRequerido) {
        this.montoRequerido = montoRequerido;
    }

    public BigDecimal getMontoRecaudado() {
        return montoRecaudado;
    }

    public void setMontoRecaudado(BigDecimal montoRecaudado) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProyectoInversion that = (ProyectoInversion) o;
        return enSuspension == that.enSuspension && Objects.equals(id, that.id) && Objects.equals(titulo, that.titulo) && Objects.equals(description, that.description) && Objects.equals(titular, that.titular) && Objects.equals(inversores, that.inversores) && Objects.equals(montoRequerido, that.montoRequerido) && Objects.equals(montoRecaudado, that.montoRecaudado) && Objects.equals(plazoParaInicio, that.plazoParaInicio) && Objects.equals(plazoParaFinal, that.plazoParaFinal) && Objects.equals(cantidadReportes, that.cantidadReportes) && Objects.equals(motivoSuspension, that.motivoSuspension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, description, titular, inversores, montoRequerido, montoRecaudado, plazoParaInicio, plazoParaFinal, cantidadReportes, enSuspension, motivoSuspension);
    }

    @Override
    public String toString() {
        return "ProyectoInversion{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", description='" + description + '\'' +
                ", titular=" + titular.getId() +
                ", inversores=" + inversores.size() +
                ", montoRequerido=" + montoRequerido +
                ", montoRecaudado=" + montoRecaudado +
                ", plazoParaInicio=" + plazoParaInicio +
                ", plazoParaFinal=" + plazoParaFinal +
                ", cantidadReportes=" + cantidadReportes +
                ", enSuspension=" + enSuspension +
                ", motivoSuspension='" + motivoSuspension + '\'' +
                '}';
    }
}
