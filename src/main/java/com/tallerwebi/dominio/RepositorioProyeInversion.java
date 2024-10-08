package com.tallerwebi.dominio;
import java.util.List;

 public interface RepositorioProyeInversion {
    List<ProyectoInversion> getMatchProyes(String name);
    List<ProyectoInversion> getPublicacionesSuspendidas();
}
