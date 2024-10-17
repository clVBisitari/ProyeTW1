package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.UsuarioDTO;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(UsuarioDTO usuario) throws UsuarioExistente;

}
