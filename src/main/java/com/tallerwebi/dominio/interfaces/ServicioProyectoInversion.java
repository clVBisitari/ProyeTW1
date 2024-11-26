package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.InversorDeProyecto;
import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.presentacion.InversorDeProyectoDTO;
import com.tallerwebi.presentacion.ProyeInversionDTO;
import com.tallerwebi.presentacion.UsuarioDTO;

import java.util.List;

public interface ServicioProyectoInversion {
    public List<ProyectoInversion> buscarProyectoInversion(String name);
    public List<ProyectoInversion> getProyectosUsuario(Integer idUsuario);
    public Integer guardarProyectoInversion(ProyeInversionDTO proyeInversionDTO, UsuarioDTO usuario);
    public ProyectoInversion editarProyectoInversion(ProyectoInversion proyeInversion);
    public boolean borrarProyectoInversion(Integer idProyeInversion);
    public boolean reportarProyecto(Integer idProyeInversion);
    public boolean suspenderProyecto(Integer idProyeInversion);
    public List<ProyectoInversion> listarPublicacionesSupendidas(Integer idUsuario);
    public Integer invertirEnProyecto(InversorDeProyectoDTO inversorDto);
}
