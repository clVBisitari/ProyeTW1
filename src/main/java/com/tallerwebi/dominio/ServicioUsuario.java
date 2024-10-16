package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public interface ServicioUsuario {
    Boolean cargarSaldo(int valor);

    Boolean registrarIngresoMensual(int valor);

    Boolean modificarIngresoMensual(int valor);

    ProyectoInversion publicarProyectoPropio(String descripción, int montoRequerido, Rubro rubro, int plazoParaInicio);

    Boolean eliminarProyectoPropio(String motivo, int idProyecto); //

    Usuario buscarUsuarioPorNombre(String nombreUsuario);

    Boolean agregarUsuarioAContactos(Usuario usuarioQueGuarda, Usuario usuarioAGuardar);

    Boolean invertirEnProyecto(int valor, int idProyecto); // ---

    void activarRecomendacionesAutomaticas(Double valor, int idUser);//buscarObjetoUSer ... // ver si sacamos parametro o no por cuestion de tiempo

    Boolean reportarProyectoSospechoso(String motivo, int idProyect, int idUser2, int idUserQueReporta); // int denuncias acumm en user

    Boolean reportarUsuarioSospechoso(String motivo, int idUserReportado, int idUserQueReporta);

    Boolean suspenderPublicacion(String motivo, int proyect);

    void suspenderUsuario(String motivo, int idUser);

    Boolean revertirSuspensionProyecto(int idProyectoInversion); // en el obj proyect inversion, modificar el boolean en suspencion

    void revertirSuspensionUsuario(int idUsuario); // en el obj proyect inversion, modificar el boolean en suspencion

    public Boolean eliminarPublicacion(int idProyectoInver);

    public Boolean eliminarUsuario(int idUser);

    List<Usuario> getContactos(String email);

    List<Usuario> getContactosSugeridos(String email);
}
