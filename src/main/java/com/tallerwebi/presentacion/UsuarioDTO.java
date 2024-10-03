package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProyectoDeInversion;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

public class UsuarioDTO {

    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private Integer dni;
    private Double saldo;
    private List<Usuario> contactos; /// lo quiero mostrar?
    //-- los otros son inversores ... en este caso, un user puede publicar un/mas proyectos de inversion
    private ProyectoDeInversion proyecto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public List<Usuario> getContactos() {
        return contactos;
    }

    public void setContactos(List<Usuario> contactos) {
        this.contactos = contactos;
    }

    public ProyectoDeInversion getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoDeInversion proyecto) {
        this.proyecto = proyecto;
    }
}
