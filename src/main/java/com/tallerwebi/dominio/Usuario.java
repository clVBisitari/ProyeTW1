package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private List<Usuario>contactos;
    private String password;
//    private String rol;// --- vendria a reemplazar es admin ?
    private Boolean esAdmin;
    private Boolean enSuspencion;
   // private Boolean activo = false; ---  este estaba del proyecto base
    //ONE TO MANY  -- los otros son inversores ... en este caso, un user puede publicar un/mas proyectos de inversion
private ProyectoDeInversion proyecto;

///private List<Inversion>
    public Long getId() {
        return id;
    }
    public String nombre() {return nombre;}
    public Integer getDni() {return dni;}

    public List<Usuario> getContactos() {
        return contactos;
    }

    public Double getSaldo() {
        return saldo;
    }

    public String getApellido() {return apellido;}
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
  //  public Boolean getActivo() {
 //       return activo;
   // }
    //public void setActivo(Boolean activo) {
    //    this.activo = activo;
  //  }

  //  public boolean activo() {
 //       return activo;
  //  }

 //   public void activar() {
 //       activo = true;
  //  }
}
