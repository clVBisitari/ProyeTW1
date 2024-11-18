package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.Usuario;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UsuarioDTO {

    private Integer id;
    private String email;
    private String nombre;
    private String apellido;
    private Integer dni;
    private boolean enSuspension;
    private boolean esAdmin;
    private List<UsuarioDTO> contactos;
    private Double saldo;

    public UsuarioDTO() {

    }

    public static Usuario convertDTOToUsuario(UsuarioDTO usuarioDTO){

        Usuario usuario = new Usuario();

        usuario.setId(usuarioDTO.getId());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setDni(usuarioDTO.getDni());
        usuario.setEnSuspension(usuarioDTO.getEnSuspension());
        usuario.setEsAdmin(usuarioDTO.getEsAdmin());
        usuario.setSaldo(usuarioDTO.getSaldo());

        return usuario;
    }

    public static UsuarioDTO convertUsuarioToDTO(Usuario usuario){

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setDni(usuario.getDni());
        usuarioDTO.setEnSuspension(usuario.getEnSuspension());
        usuarioDTO.setEsAdmin(usuario.getEsAdmin());
        usuarioDTO.setSaldo(usuario.getSaldo());

        if (usuario.getContactos() != null) {
            List<UsuarioDTO> contactosDTO = usuario.getContactos().stream()
                    .map(UsuarioDTO::convertUsuarioToDTO)
                    .collect(Collectors.toList());
            usuarioDTO.setContactos(contactosDTO);
        }

        return usuarioDTO;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public void setEnSuspension(boolean enSuspension) {
        this.enSuspension = enSuspension;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public void setContactos(List<UsuarioDTO> contactos) {
        this.contactos = contactos;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Integer getDni() {
        return dni;
    }

    public boolean getEnSuspension() {
        return enSuspension;
    }

    public boolean getEsAdmin() {
        return esAdmin;
    }

    public List<UsuarioDTO> getContactos() {
        return contactos;
    }

    public Double getSaldo() {
        return saldo;
    }

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
                "id=" + id +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", enSuspension=" + enSuspension +
                ", esAdmin=" + esAdmin +
                ", saldo=" + saldo +
                '}';
    }
}
