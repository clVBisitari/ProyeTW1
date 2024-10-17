package com.tallerwebi.dominio.interfaces;
import com.tallerwebi.dominio.ProyectoInversion;

import java.util.List;

 public interface RepositorioProyeInversion {
    List<ProyectoInversion> getMatchProyes(String name);
    List<ProyectoInversion> getPublicacionesSuspendidas(Integer idUsuario);
    List<ProyectoInversion>getProyectosInversion(Integer idUsuario);
    ProyectoInversion getProyectoById(Long idProyeInversion);
    Long saveProyectoInversion(ProyectoInversion proyeInversion);
    ProyectoInversion updateProyeInversion(ProyectoInversion proyeInversion);
    boolean deleteProyeInversion(Long idProyeInversion);
    boolean reportProyeInversion(Long idProyeInversion);
    boolean suspenderProyecto(Long idProyeInversion);
}
