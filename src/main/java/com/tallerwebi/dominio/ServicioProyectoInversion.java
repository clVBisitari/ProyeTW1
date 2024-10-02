package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioProyectoInversion {
    public List<ProyectoInversion> buscarProyectoInversion(String name);
    public List<ProyectoInversion> listarPublicacionesSupendidas();
}
