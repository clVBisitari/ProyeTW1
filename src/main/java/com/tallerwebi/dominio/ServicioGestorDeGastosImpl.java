package com.tallerwebi.dominio;

import org.hsqldb.SchemaObjectSet;

import javax.persistence.*;
import java.util.*;

@Entity
public class ServicioGestorDeGastosImpl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "gestor", orphanRemoval = true)
    List<Gasto> gastos = new ArrayList<Gasto>();


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void registrarGasto(Gasto gasto) {
        this.gastos.add(gasto);
        gasto.setGestor(this);
    }

    public void eliminarGasto(Gasto gasto) {
        this.gastos.remove(gasto);
        gasto.setGestor(null);
    }

    /*public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }*/
}
