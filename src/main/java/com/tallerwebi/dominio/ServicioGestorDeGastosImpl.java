package com.tallerwebi.dominio;

import org.hsqldb.SchemaObjectSet;

import javax.persistence.*;
import java.util.*;

@Entity
public class ServicioGestorDeGastosImpl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "gestor")
    List<Gasto> gastos = new ArrayList<Gasto>();


    public void registrarGasto(Gasto gasto) {
        gastos.add(gasto);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }
}
