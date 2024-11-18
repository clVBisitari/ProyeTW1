package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.*;

@Entity
public class GestorDeGastos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    Usuario usuario;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "gestor", orphanRemoval = true)
    private List<Gasto> gastos = new ArrayList<Gasto>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void eliminarGasto(Gasto gasto) {
        this.gastos.remove(gasto);
        gasto.setGestor(null);
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {this.gastos = gastos;}

    @Override
    public String toString() {
        return "GestorDeGastos{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", gastos=" + gastos +
                '}';
    }
}
