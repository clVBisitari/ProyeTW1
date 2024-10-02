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

}
