package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.UsuarioDTO;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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
    private BigDecimal saldo;
    private String motivoDeSuspension;
    private Boolean estaActivo = true;
    @ManyToMany
    @JoinTable(
            name = "usuario_usuarios",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "contacto_id")
    )
    private List<Usuario> contactos = new ArrayList<>();
    private String password;
    private Boolean esAdmin = false;
    private Boolean enSuspension = false;
    @OneToMany(mappedBy = "titular", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProyectoInversion> proyectos;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gasto> gastos = new ArrayList<Gasto>();
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Saldo> saldos;
    private String cvu;

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public List<Saldo> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<Saldo> saldos) {
        this.saldos = saldos;
    }


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
    public String getEmail() {return email;}
    public String getNombre() {return nombre;}
    public List<Gasto> getGastos(){return this.gastos;};
    public String getApellido() {return apellido;}
    public Integer getDni() {return dni;}
    public BigDecimal getSaldo() {return saldo;}
    public List<Usuario> getContactos() {return contactos;}

    public void setId(Integer id) {this.id = id;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setEmail(String email) {this.email = email;}
    public void setApellido(String apellido) {this.apellido = apellido;}
    public void setDni(Integer dni) {this.dni = dni;}
    public void setSaldo(BigDecimal saldo) {this.saldo = saldo;}
    public String getPassword() {return password;}
    public Boolean getEsAdmin() {return esAdmin;}
    public Boolean getEnSuspension() {return enSuspension;}
    public List<ProyectoInversion> getProyectos() {return proyectos;}
    public Boolean getEstaActivo() {return this.estaActivo;}
    public void setContactos(List<Usuario> contactos) {this.contactos = contactos;}
    public void setPassword(String password) {this.password = password;}
    public void setEsAdmin(Boolean esAdmin) {this.esAdmin = esAdmin;}
    public void setEnSuspension(Boolean enSuspension) {this.enSuspension = enSuspension;}
    public void setProyectos(List<ProyectoInversion> proyecto) {this.proyectos = proyecto;}
    public void setEstaActivo(Boolean activo) {this.estaActivo = activo;}
    public void setGastos(List<Gasto>gastos){this.gastos= gastos;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(email, usuario.email) && Objects.equals(nombre, usuario.nombre) && Objects.equals(apellido, usuario.apellido) && Objects.equals(dni, usuario.dni) && Objects.equals(saldo, usuario.saldo) && Objects.equals(contactos, usuario.contactos) && Objects.equals(password, usuario.password) && Objects.equals(esAdmin, usuario.esAdmin) && Objects.equals(enSuspension, usuario.enSuspension) && Objects.equals(proyectos, usuario.proyectos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nombre, apellido, dni, saldo, contactos, password, esAdmin, enSuspension, proyectos);
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
                ", enSuspension=" + enSuspension +
                ", proyecto=" + proyectos +
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
