package com.tallerwebi.dominio;

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

    @ManyToOne
    @JoinColumn(name = "gestor_id", nullable = false)
    private ServicioGestorDeGastosImpl gestor;

    public Gasto() {}

    public Gasto(String descripcion, double valor) {
        this.descripcion = descripcion;
        this.valor = valor;
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

   /* public ServicioGestorDeGastosImpl getGestor() {
        return gestor;
    }
    public void setGestor(ServicioGestorDeGastosImpl gestor) {
        this.gestor = gestor;
    }*/
}
