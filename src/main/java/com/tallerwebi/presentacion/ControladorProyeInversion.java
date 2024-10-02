package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.ServicioProyectoInversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControladorProyeInversion {

    private ServicioProyectoInversion servicioProyectoInversion;

    @Autowired
    public ControladorProyeInversion(ServicioProyectoInversion proyectoInversion) {
        this.servicioProyectoInversion = proyectoInversion;
    }
    @RequestMapping
    public List<ProyectoInversion> buscarProyectoInversion(String nombre)
    {
        List<ProyectoInversion> proyectos = this.servicioProyectoInversion.buscarProyectoInversion(nombre);

    }

}
