package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);
    Usuario buscarUsuarioPorId(int id);
    List<Usuario> obtenerContactos(String email);

    Usuario buscarUsuarioPorNombre(String nombre);
}


