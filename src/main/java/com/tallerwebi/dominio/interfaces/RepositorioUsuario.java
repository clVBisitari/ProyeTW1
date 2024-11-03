package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Usuario;

import java.util.List;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    boolean guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);
    Usuario buscarUsuarioPorId(int id);
    List<Usuario> obtenerContactos(String email);
    List<Usuario>getContactosSugeridos(Integer id);
    List<Usuario> buscarUsuarioPorNombre(String nombreUsuario);
}


