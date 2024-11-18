package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
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
    private double valor;

//    @JsonIgnore
//    @JoinTable(name ="gestor")
//    private GestorDeGastos gestor;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate fechaRecordatorio;

    @Column
    private Frecuencia frecuencia;

    public Gasto() {}

    public Gasto(String descripcion, double valor, LocalDate fechaVencimiento, Frecuencia frecuencia) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.fechaVencimiento = fechaVencimiento;
        this.frecuencia = frecuencia;
    }

    public Gasto(String descripcion, double valor, LocalDate fechaVencimiento, LocalDate fechaRecordatorio, Frecuencia frecuencia) {
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

    public double getValor() {
        return valor;
    }
    public Usuario getUsuario(){return usuario;}

//    public GestorDeGastos getGestor() {
//        return gestor;
//    }
//    public void setGestor(GestorDeGastos gestor) {
//        this.gestor = gestor;
//    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public LocalDate getFechaRecordatorio() {
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

    public void setValor(double valor) {
        this.valor = valor;
    }
    public void setUsuario(Usuario usuario){this.usuario = usuario;}
/*    public void setGestorId(Long gestorId) {
        this.gestorId = gestorId;
    }

    public void setFechaRecordatorio(LocalDate fechaRecordatorio) {
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
        return Double.compare(valor, gasto.valor) == 0 && Objects.equals(id, gasto.id) && Objects.equals(descripcion, gasto.descripcion) && Objects.equals(gestor, gasto.gestor) && Objects.equals(fechaVencimiento, gasto.fechaVencimiento) && Objects.equals(fechaRecordatorio, gasto.fechaRecordatorio) && frecuencia == gasto.frecuencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion, valor, gestor, fechaVencimiento, fechaRecordatorio, frecuencia);
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", valor=" + valor +
                ", gestor=" + gestor.getId() +
                ", fechaVencimiento=" + fechaVencimiento +
                ", fechaRecordatorio=" + fechaRecordatorio +
                ", frecuencia=" + frecuencia +
                '}';
    }
}
