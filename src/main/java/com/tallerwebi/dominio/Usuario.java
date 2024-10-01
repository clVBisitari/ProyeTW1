package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private Integer dni;
    private Double saldo;
    @OneToMany
    private List<Usuario>contactos;
    private String password;
    private Boolean esAdmin;
    private Boolean enSuspencion;
   @OneToOne //-- los otros son inversores ... en este caso, un user puede publicar un/mas proyectos de inversion
    private ProyectoDeInversion proyecto;
   ///private List<Inversion>


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public Boolean getEnSuspencion() {
        return enSuspencion;
    }

    public void setEnSuspencion(Boolean enSuspencion) {
        this.enSuspencion = enSuspencion;
    }

    public ProyectoDeInversion getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoDeInversion proyecto) {
        this.proyecto = proyecto;
    }
}
