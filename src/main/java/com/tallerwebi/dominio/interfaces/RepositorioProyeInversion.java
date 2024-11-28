package com.tallerwebi.dominio.interfaces;
import com.tallerwebi.dominio.InversorDeProyecto;
import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.InversionDTO;

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
    Integer saveInversor(InversorDeProyecto inversor, ProyectoInversion proyectoInversion, Usuario usuario);
    List<ProyectoInversion> getProyectosMayorInversion();
    List<InversionDTO> getInversionesByUser(Integer userId);
}
