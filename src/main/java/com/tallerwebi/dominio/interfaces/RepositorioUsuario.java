package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.Saldo;
import com.tallerwebi.dominio.Usuario;

import java.math.BigDecimal;
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

    List<Usuario> getUsuariosSuspendidos();

    void agregarContacto(Integer idUsuario, Integer idContacto);
    List<ProyectoInversion> getProyectosRecomendados(Integer usuarioId, BigDecimal saldo);
    public List<Saldo> getSaldosByUserId(Integer idUsuario);
    public boolean saveSaldoFromUser(Saldo saldo);
}


