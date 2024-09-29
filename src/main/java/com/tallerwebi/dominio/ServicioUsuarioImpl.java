package com.tallerwebi.dominio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service("ServicioUsuario")
@Transactional

public class ServicioUsuarioImpl implements ServicioUsuario{

    private RepositorioUsuario repositorioUsuario;

    @Autowired // tambien iria repositorioUsuario ??
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }


    @Override
    public Boolean cargarSaldo(int valor) {
        return null;
    }

    @Override
    public Boolean registrarIngresoMensual(int valor) {
        return null;
    }

    @Override
    public Boolean modificarIngresoMensual(int valor) {
        return null;
    }

    @Override
    public Boolean publicarProyectoPropio(String descripción, int montoRequerido, Rubro rubro, int plazoParaInicio) {
        return null;
    }

    @Override
    public Boolean eliminarProyectoPropio(String motivo) {
        return null;
    }

    @Override
    public Usuario buscarUsuario(String nombreUsuario) {
        return null;
    }

    @Override
    public Boolean agregarUsuarioAContactos(Usuario usuarioAGuardar) {
        return null;
    }

    @Override
    public ProyectoDeInversion buscarProyectosDeInversion() {
        return null;
    }

    @Override
    public Boolean invertirEnProyecto(int valor) {
        return null;
    }

    @Override
    public Boolean activarRecomendacionesAutomaticas(int valor) {
        return null;
    }

    @Override
    public Boolean reportarProyectoSospechoso(String motivo) {
        return null;
    }

    @Override
    public Boolean reportarUsuarioSospechoso(String motivo) {
        return null;
    }

    @Override
    public Boolean suspenderPublicacion(String motivo) {
        return null;
    }

    @Override
    public Boolean suspenderUsuario(String motivo) {
        return null;
    }

    @Override
    public Boolean listarPublicacionesSuspendidas() {
        return null;
    }

    @Override
    public Usuario buscarUsuario(Usuario nombreUsuario) {
        return null;
    }

    @Override
    public Boolean revertirSuspension() {
        return null;
    }

    @Override
    public Boolean eliminarPublicacion() {
        return null;
    }

    @Override
    public Boolean eliminarUsuario() {
        return null;
    }
}
