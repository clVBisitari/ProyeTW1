package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal valor;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private Date fechaVencimiento;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column
    private Date fechaRecordatorio;

    @Column
    private Frecuencia frecuencia;

    public Gasto() {}

    public Gasto(String descripcion, BigDecimal valor, Date fechaVencimiento, Frecuencia frecuencia) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.fechaVencimiento = fechaVencimiento;
        this.frecuencia = frecuencia;
    }

    public Gasto(String descripcion, BigDecimal valor, Date fechaVencimiento,Date fechaRecordatorio, Frecuencia frecuencia) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaRecordatorio = fechaRecordatorio;
        this.frecuencia = frecuencia;
    }

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getValor() {
        return valor;
    }
    public Usuario getUsuario(){return usuario;}

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public Date getFechaRecordatorio() {
        return fechaRecordatorio;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public void setUsuario(Usuario usuario){this.usuario = usuario;}

    public void setFechaRecordatorio(Date fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gasto gasto = (Gasto) o;
        return valor.compareTo(gasto.valor) == 0 && Objects.equals(id, gasto.id) && Objects.equals(descripcion, gasto.descripcion) && Objects.equals(fechaVencimiento, gasto.fechaVencimiento) && Objects.equals(fechaRecordatorio, gasto.fechaRecordatorio) && frecuencia == gasto.frecuencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion, valor, fechaVencimiento, fechaRecordatorio, frecuencia);
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", valor=" + valor +
                ", fechaVencimiento=" + fechaVencimiento +
                ", fechaRecordatorio=" + fechaRecordatorio +
                ", frecuencia=" + frecuencia +
                '}';
    }
}
