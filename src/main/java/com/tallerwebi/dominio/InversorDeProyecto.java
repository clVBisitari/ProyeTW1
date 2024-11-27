package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.InversorDeProyectoDTO;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="inversor_proyecto")
public class InversorDeProyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;
    @OneToOne
    @JoinColumn(name ="id_proyecto")
    private ProyectoInversion proyecto;
    private BigDecimal monto;

    public ProyectoInversion getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoInversion proyecto) {
        this.proyecto = proyecto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static InversorDeProyecto convertToInversorDeProyecto(InversorDeProyectoDTO invProyeDTO) {
        InversorDeProyecto inversorDeProyecto = new InversorDeProyecto();
        inversorDeProyecto.setMonto(new BigDecimal(invProyeDTO.monto));
        return inversorDeProyecto;
    }
}
