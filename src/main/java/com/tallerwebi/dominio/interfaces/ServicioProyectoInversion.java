package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.ProyectoInversion;

import java.util.List;

public interface ServicioProyectoInversion {
    public List<ProyectoInversion> buscarProyectoInversion(String name);
    public List<ProyectoInversion> getProyectosUsuario(Integer idUsuario);
    public Long guardarProyectoInversion( ProyectoInversion proyeInversion);
    public ProyectoInversion editarProyectoInversion(ProyectoInversion proyeInversion);
    public boolean borrarProyectoInversion(Long idProyeInversion);
    public boolean reportarProyecto(Long idProyeInversion);
    public boolean suspenderProyecto(Long idProyeInversion);
    public List<ProyectoInversion> listarPublicacionesSupendidas(Integer idUsuario);
}
