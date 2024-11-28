package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.Saldo;
import com.tallerwebi.dominio.Usuario;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UsuarioDTO {

    private Integer id;
    private String email;
    private String nombre;
    private String apellido;
    private Integer dni;
    private String motivoDeSuspension;
    private boolean enSuspension;
    private boolean esAdmin;
    private boolean estaActivo;
    private List<UsuarioDTO> contactos;
    private BigDecimal saldo;
    private String cvu;
    private List<Saldo> saldos;


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
        usuario.setMotivoDeSuspension(usuarioDTO.getMotivoDeSuspension());
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
        usuarioDTO.setContactos(Usuario.mapToUsuarioDTOList(usuario.getContactos()));
        usuarioDTO.setEstaActivo(usuario.getEstaActivo());
        usuarioDTO.setCvu(usuario.getCvu());
        return usuarioDTO;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }

    public boolean isEnSuspension() {
        return enSuspension;
    }

    public boolean isEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    public String getMotivoDeSuspension() {
        return  this.motivoDeSuspension;
    }

    public void setMotivoDeSuspension(String motivo) {
        this.motivoDeSuspension = motivo;
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

    public void setSaldo(BigDecimal saldo) {
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

    public BigDecimal getSaldo() {
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

    public Usuario convertToUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDni(dni);
        usuario.setEnSuspension(enSuspension);
        usuario.setEsAdmin(esAdmin);
        usuario.setSaldo(saldo);
        return usuario;
    }
}
