package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.time.LocalDate;

@Entity
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private double valor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private GestorDeGastos gestor;

    @Column(nullable = false)
    private Date fechaVencimiento;

    @Column
    private Date fechaRecordatorio;

    @Column
    private Frecuencia frecuencia;

//    @Column
  //  private Long gestorId;

    public Gasto() {}

    public Gasto(String descripcion, double valor, Date fechaVencimiento, Frecuencia frecuencia) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.fechaVencimiento = fechaVencimiento;
        this.frecuencia = frecuencia;
    }

    public Gasto(String descripcion, double valor, Date fechaVencimiento, Date fechaRecordatorio, Frecuencia frecuencia) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaRecordatorio = fechaRecordatorio;
        this.frecuencia = frecuencia;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public GestorDeGastos getGestor() {
        return gestor;
    }
    public void setGestor(GestorDeGastos gestor) {
        this.gestor = gestor;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaRecordatorio(Date fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }

    public Date getFechaRecordatorio() {
        return fechaRecordatorio;
    }

    public void setFrecuencia(Frecuencia frecuencia){
        this.frecuencia = frecuencia;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
/*    public void setGestorId(Long gestorId) {
        this.gestorId = gestorId;
    }

    public Long getGestorId() {
        return gestorId;
    }*/
}
