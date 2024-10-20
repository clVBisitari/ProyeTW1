package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String nombre;
    private String apellido;
    private Integer dni;
    private Double saldo;
    @OneToMany
    @JoinColumn(name = "usuario_id")
    private List<Usuario>contactos;
    private String password;
    private Boolean esAdmin = false;
    private Boolean enSuspension = false;
    @OneToOne //-- los otros son inversores ... en este caso, un user puede publicar un/mas proyectos de inversion
    private ProyectoInversion proyecto;
    @OneToOne
    private GestorDeGastos gestorDeGastos;

    public Usuario(){}
    public Usuario(String email, String password, String nombre, String apellido, Integer dni) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GestorDeGastos getGestorDeGastos() {
        return gestorDeGastos;
    }

    public void setGestorDeGastos(GestorDeGastos gestorDeGastos) {
        this.gestorDeGastos = gestorDeGastos;
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

    public Boolean getEnSuspension() {
        return enSuspension;
    }

    public void setEnSuspension(Boolean enSuspencion) {
        this.enSuspension = enSuspencion;
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
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(email, usuario.email) && Objects.equals(nombre, usuario.nombre) && Objects.equals(apellido, usuario.apellido) && Objects.equals(dni, usuario.dni) && Objects.equals(saldo, usuario.saldo) && Objects.equals(contactos, usuario.contactos) && Objects.equals(password, usuario.password) && Objects.equals(esAdmin, usuario.esAdmin) && Objects.equals(enSuspension, usuario.enSuspension) && Objects.equals(proyecto, usuario.proyecto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nombre, apellido, dni, saldo, contactos, password, esAdmin, enSuspension, proyecto);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", saldo=" + saldo +
                ", contactos=" + contactos +
                ", password='" + password + '\'' +
                ", esAdmin=" + esAdmin +
                ", enSuspencion=" + enSuspension +
                ", proyecto=" + proyecto +
                '}';
    }
}
