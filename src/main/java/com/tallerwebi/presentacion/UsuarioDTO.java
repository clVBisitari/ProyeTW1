package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsuarioDTO {
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private Integer dni;
    private boolean enSuspension;
    private boolean esAdmin;
    private List<UsuarioDTO> contactos;
    private Double saldo;

    public UsuarioDTO() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

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

    public void setEnSuspension(boolean enSuspension) {this.enSuspension = enSuspension;}

    public void setContactos(List<UsuarioDTO> contactosDTO) {this.contactos = contactosDTO;}

    public boolean isEnSuspension() {return enSuspension;}

    public List<UsuarioDTO> getContactos() {return contactos;}

    public boolean isEsAdmin() {return esAdmin;}

    public void setEsAdmin(boolean esAdmin) {this.esAdmin = esAdmin;}

    public Double getSaldo() {return saldo;}

    public void setSaldo(Double saldo) {this.saldo = saldo;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDTO that = (UsuarioDTO) o;
        return Objects.equals(email, that.email) && Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido) && Objects.equals(dni, that.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, nombre, apellido, dni);
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
