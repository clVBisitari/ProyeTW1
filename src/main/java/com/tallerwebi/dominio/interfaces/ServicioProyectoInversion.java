package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.presentacion.ProyeInversionDTO;
import com.tallerwebi.presentacion.UsuarioDTO;

import java.util.List;

public interface ServicioProyectoInversion {
    List<ProyectoInversion> buscarProyectoInversion(String name);
    List<ProyectoInversion> getProyectosUsuario(Integer idUsuario);
    Integer guardarProyectoInversion(ProyeInversionDTO proyeInversionDTO, UsuarioDTO usuario);
    ProyectoInversion editarProyectoInversion(ProyectoInversion proyeInversion);
    boolean borrarProyectoInversion(Long idProyeInversion);
    boolean reportarProyecto(Long idProyeInversion);
    boolean suspenderProyecto(Long idProyeInversion);
    List<ProyectoInversion> listarPublicacionesSupendidas(Integer idUsuario);
    List<ProyectoInversion> getProyectosMayorInversion();
}
