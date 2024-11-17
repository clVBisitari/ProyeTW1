package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class GestorDeGastos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Usuario usuario;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "gestor", orphanRemoval = true)
//    private List<Gasto> gastos = new ArrayList<Gasto>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

//    public void registrarGasto(Gasto gasto) {
//        gasto.setGestorId(this.id);
//        this.gastos.add(gasto);
//    }
//
//    public void eliminarGasto(Gasto gasto) {
//
//        this.gastos.remove(gasto);
//    }

//    public List<Gasto> getGastos() {
//        return gastos;
//    }
//
//    public void setGastos(List<Gasto> gastos) {this.gastos = gastos;}
    public void setUsuario(Usuario usuario){this.usuario = usuario;}
}
