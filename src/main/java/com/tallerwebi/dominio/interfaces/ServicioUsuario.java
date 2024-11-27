package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.Rubro;
import com.tallerwebi.dominio.Usuario;

import java.math.BigDecimal;
import java.util.List;

public interface ServicioUsuario {

    Usuario getUsuarioById(Integer Id);
    Boolean cargarSaldo(BigDecimal valor, int idUser) throws Exception;
   /* ProyectoInversion publicarProyectoPropio(String descripción, int montoRequerido, Rubro rubro, int plazoParaInicio);*/
    List<Usuario> buscarUsuarioPorNombre(String nombreUsuario);
    Boolean agregarUsuarioAContactos(Usuario usuarioQueGuarda, Usuario usuarioAGuardar) throws Exception;
  /*  Boolean invertirEnProyecto(int valor, int idProyecto);*/
   /* void activarRecomendacionesAutomaticas(Double valor, int idUser);*/
    Boolean reportarProyectoSospechoso(String motivo, int idProyect, int idUser2, int idUserQueReporta); // int denuncias acumm en user
    /*Boolean reportarUsuarioSospechoso(String motivo, int idUserReportado, int idUserQueReporta);*/
    Boolean suspenderPublicacion(String motivo, int proyect);
    Boolean suspenderUsuario(String motivo, int idUser);
    Boolean revertirSuspensionProyecto(int idProyectoInversion);
    Boolean revertirSuspensionUsuario(int idUsuario);
    public Boolean eliminarPublicacion(int idProyectoInver);
    List<Usuario> getContactos(String email);
    List<Usuario> getContactosSugeridos(Integer id);
    Usuario buscarUsuarioPorId(Integer id);
    List<Usuario> getUsuariosSuspedidos();
    Boolean eliminarUsuarioDeContactos(Usuario usuarioQueElimina, Usuario usuarioAEliminar);
    List<ProyectoInversion> obtenerProyectosRecomendados(Integer usuarioId) throws Exception;
    void cambiarEstadoUsuario(Usuario usuario) throws Exception;
}
