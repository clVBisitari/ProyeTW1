package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsuarioDTO {

    private Integer id;
    private String email;
    private String nombre;
    private String apellido;
    private Integer dni;
    private Double saldo;
    private List<UsuarioDTO> contactos; /// lo quiero mostrar?
    //-- los otros son inversores ... en este caso, un user puede publicar un/mas proyectos de inversion
    private ProyectoInversion proyecto;
    private Boolean esAdmin;
    private Boolean enSuspencion;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEnSuspencion() {
        return enSuspencion;
    }

    public void setEnSuspencion(Boolean enSuspencion) {
        this.enSuspencion = enSuspencion;
    }

    public Boolean getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
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

    public List<UsuarioDTO> getContactos() {
        return contactos;
    }

    public void setContactos(List<UsuarioDTO> contactos) {
        this.contactos = contactos;
    }

    public ProyectoInversion getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoInversion proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDTO that = (UsuarioDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido) && Objects.equals(dni, that.dni) && Objects.equals(saldo, that.saldo) && Objects.equals(contactos, that.contactos) && Objects.equals(proyecto, that.proyecto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nombre, apellido, dni, saldo, contactos, proyecto);
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", saldo=" + saldo +
                ", contactos=" + contactos +
                ", proyecto=" + proyecto +
                ", esAdmin=" + esAdmin +
                ", enSuspencion=" + enSuspencion +
                '}';
    }
}
