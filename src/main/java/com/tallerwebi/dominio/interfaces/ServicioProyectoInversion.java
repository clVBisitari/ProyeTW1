package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.presentacion.InversionDTO;
import com.tallerwebi.presentacion.InversorDeProyectoDTO;
import com.tallerwebi.presentacion.ProyeInversionDTO;
import com.tallerwebi.presentacion.UsuarioDTO;

import java.util.List;

public interface ServicioProyectoInversion {
    List<ProyectoInversion> buscarProyectoInversion(String name);
    List<ProyectoInversion> getProyectosUsuario(Integer idUsuario);
    Integer guardarProyectoInversion(ProyeInversionDTO proyeInversionDTO, UsuarioDTO usuario);
    ProyectoInversion editarProyectoInversion(ProyectoInversion proyeInversion);
    boolean borrarProyectoInversion(Integer idProyeInversion);
    boolean reportarProyecto(Integer idProyeInversion);
    boolean suspenderProyecto(Integer idProyeInversion);
    List<ProyectoInversion> listarPublicacionesSupendidas(Integer idUsuario);
    public Integer invertirEnProyecto(InversorDeProyectoDTO inversorDto);
    List<ProyectoInversion> getProyectosMayorInversion();
    ProyeInversionDTO getProyectoInversionPorId(Integer id);
    public List<InversionDTO>getInversionesPorUsuario(Integer userId);
}
