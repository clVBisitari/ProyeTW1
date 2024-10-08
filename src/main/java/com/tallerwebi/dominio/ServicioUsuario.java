package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public interface ServicioUsuario {
    public Boolean cargarSaldo(int valor);
    public Boolean registrarIngresoMensual(int valor);
    public Boolean modificarIngresoMensual(int valor);
    public ProyectoInversion publicarProyectoPropio(String descripción,int montoRequerido, Rubro rubro,int plazoParaInicio);
    public Boolean eliminarProyectoPropio(String motivo,int idProyecto); //
    public Usuario buscarUsuario(String nombreUsuario);
    public Boolean agregarUsuarioAContactos(Usuario usuarioAGuardar);
    public Boolean invertirEnProyecto(int valor,int idProyecto); // ---
    public void activarRecomendacionesAutomaticas(Double valor,int idUser);//buscarObjetoUSer ... // ver si sacamos parametro o no por cuestion de tiempo
    public Boolean reportarProyectoSospechoso(String motivo, int idProyect, int idUser2,  int idUserQueReporta); // int denuncias acumm en user
    public Boolean reportarUsuarioSospechoso(String motivo, int idUserReportado, int idUserQueReporta);
    public Boolean suspenderPublicacion(String motivo, int proyect);
    public Boolean suspenderUsuario(String motivo, int idUser);
    public List<Usuario> buscarUsuario(Usuario nombreUsuario); /// dev una lista y selecciona con el nombre -- de la mando de agregar user a contactos
    public Boolean revertirSuspensionProyecto(int idProyectoInversion); // en el obj proyect inversion, modificar el boolean en suspencion
    public Boolean revertirSuspensionUsuario(int idUsuario); // en el obj proyect inversion, modificar el boolean en suspencion

    public Boolean eliminarPublicacion(int idProyectoInver);
    public Boolean eliminarUsuario(int idUser);

    ArrayList<Usuario> getContactos();
}
