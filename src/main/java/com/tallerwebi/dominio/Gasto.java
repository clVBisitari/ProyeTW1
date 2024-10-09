package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

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
    private String fechaVencimiento;

    @Column
    private String fechaRecordatorio;

    @Column
    private Frecuencia frecuencia;

//    @Column
  //  private Long gestorId;

    public Gasto() {}

    public Gasto(String descripcion, double valor, String fechaVencimiento, Frecuencia frecuencia) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.fechaVencimiento = fechaVencimiento;
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

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaRecordatorio(String fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }

    public String getFechaRecordatorio() {
        return fechaRecordatorio;
    }

    public void setFrecuencia(Frecuencia frecuencia){
        this.frecuencia = frecuencia;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

/*    public void setGestorId(Long gestorId) {
        this.gestorId = gestorId;
    }

    public Long getGestorId() {
        return gestorId;
    }*/
}
