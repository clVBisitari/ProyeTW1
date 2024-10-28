package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.Rubro;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

public interface ServicioUsuario {

    Usuario getUsuarioById(Integer Id);
    Boolean cargarSaldo(int valor);
    Boolean registrarIngresoMensual(int valor);
    Boolean modificarIngresoMensual(int valor);
    ProyectoInversion publicarProyectoPropio(String descripción, int montoRequerido, Rubro rubro, int plazoParaInicio);
    Boolean eliminarProyectoPropio(String motivo, int idProyecto);
    List<Usuario> buscarUsuarioPorNombre(String nombreUsuario);
    Boolean agregarUsuarioAContactos(Usuario usuarioQueGuarda, Usuario usuarioAGuardar);
    Boolean invertirEnProyecto(int valor, int idProyecto);
    void activarRecomendacionesAutomaticas(Double valor, int idUser);
    Boolean reportarProyectoSospechoso(String motivo, int idProyect, int idUser2, int idUserQueReporta); // int denuncias acumm en user
    Boolean reportarUsuarioSospechoso(String motivo, int idUserReportado, int idUserQueReporta);
    Boolean suspenderPublicacion(String motivo, int proyect);
    void suspenderUsuario(String motivo, int idUser);
    List<Usuario> buscarUsuario(Usuario nombreUsuario);
    Boolean revertirSuspensionProyecto(int idProyectoInversion);
    void revertirSuspensionUsuario(int idUsuario);
    public Boolean eliminarPublicacion(int idProyectoInver);
    public Boolean eliminarUsuario(int idUser);
    List<Usuario> getContactos(String email);
    List<Usuario> getContactosSugeridos(String email);
}
