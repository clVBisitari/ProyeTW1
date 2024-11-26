package com.tallerwebi.dominio.interfaces;
import com.tallerwebi.dominio.InversorDeProyecto;
import com.tallerwebi.dominio.ProyectoInversion;

import java.util.List;

 public interface RepositorioProyeInversion {
    List<ProyectoInversion> getMatchProyes(String name);
    List<ProyectoInversion> getPublicacionesSuspendidas(Integer idUsuario);
    List<ProyectoInversion>getProyectosInversion(Integer idUsuario);
    ProyectoInversion getProyectoById(Integer idProyeInversion);
    Integer saveProyectoInversion(ProyectoInversion proyeInversion);
    ProyectoInversion updateProyeInversion(ProyectoInversion proyeInversion);
    boolean deleteProyeInversion(Integer idProyeInversion);
    boolean reportProyeInversion(Integer idProyeInversion);
    boolean suspenderProyecto(Integer idProyeInversion);
    Integer saveInversor(InversorDeProyecto inversor, ProyectoInversion proyectoInversion);
    List<ProyectoInversion> getProyectosMayorInversion();
}
