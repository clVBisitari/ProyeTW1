package com.tallerwebi.dominio;

import java.util.List;

public class ServicioUsuarioImpl implements ServicioUsuario{
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
    public ProyectoDeInversion publicarProyectoPropio(String descripción, int montoRequerido, Rubro rubro, int plazoParaInicio) {
        return null;
    }

    @Override
    public Boolean eliminarProyectoPropio(String motivo, int idProyecto) {
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
    public Boolean invertirEnProyecto(int valor, int idProyecto) {
        return null;
    }

    @Override
    public void activarRecomendacionesAutomaticas(Double valor, int idUser) {

    }

    @Override
    public Boolean reportarProyectoSospechoso(String motivo, int idProyect, int idUser2, int idUserQueReporta) {
        return null;
    }

    @Override
    public Boolean reportarUsuarioSospechoso(String motivo, int idUserReportado, int idUserQueReporta) {
        return null;
    }

    @Override
    public Boolean suspenderPublicacion(String motivo, int proyect) {
        return null;
    }

    @Override
    public Boolean suspenderUsuario(String motivo, int idUser) {
        return null;
    }

    @Override
    public List<Usuario> buscarUsuario(Usuario nombreUsuario) {
        return List.of();
    }

    @Override
    public Boolean revertirSuspensionProyecto(int idProyectoInversion) {
        return null;
    }

    @Override
    public Boolean revertirSuspensionUsuario(int idUsuario) {
        return null;
    }

    @Override
    public Boolean eliminarPublicacion(int idProyectoInver) {
        return null;
    }

    @Override
    public Boolean eliminarUsuario(int idUser) {
        return null;
    }
}
