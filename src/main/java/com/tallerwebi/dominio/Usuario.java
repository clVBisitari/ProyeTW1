package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.UsuarioDTO;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String email;
    private String nombre;
    private String apellido;
    private Integer dni;
    private Double saldo;
    private String motivoDeSuspension;
    private Boolean estaActivo = true;
    @ManyToMany
    @JoinColumn(name = "usuario_id")
    private List<Usuario>contactos;
    private String password;
    private Boolean esAdmin = false;
    private Boolean enSuspension = false;
    @OneToOne //-- los otros son inversores ... en este caso, un user puede publicar un/mas proyectos de inversion
    private ProyectoInversion proyecto;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "usuario", orphanRemoval = true)
    private GestorDeGastos gestorDeGastos;
    ///private List<Inversion>

    public Usuario(){}

    public Usuario(String email, String password, String nombre, String apellido, Integer dni) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    public String getMotivoDeSuspension() {
        return  this.motivoDeSuspension;
    }
    public void setMotivoDeSuspension(String motivo) {
        this.motivoDeSuspension = motivo;
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

    public Boolean getEstaActivo() {
        return this.estaActivo;
    }

    public void setEstaActivo(Boolean activo) {
        this.estaActivo = activo;
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
    public static boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        return session != null && isAttributeNotNull(session, "USUARIO") && isAttributeNotNull(session, "idUsuario");
    }
    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        UsuarioDTO usuarioDto = (UsuarioDTO) session.getAttribute("USUARIO");
        return session != null && isAttributeNotNull(session, "USUARIO") && usuarioDto.getEsAdmin();
    }


    public static Boolean isAttributeNotNull(HttpSession session, String attributeName){
        return session.getAttribute(attributeName) != null;
    }

    public static List<UsuarioDTO> mapToUsuarioDTOList(List<Usuario> usuarios) {
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();

            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setNombre(usuario.getNombre());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setMotivoDeSuspension(usuario.getMotivoDeSuspension());
            usuarioDTO.setEnSuspension(usuario.getEnSuspension());
            usuarioDTO.setEstaActivo(usuario.getEstaActivo());
            usuariosDTO.add(usuarioDTO);
        }
        return usuariosDTO;
    }
}
