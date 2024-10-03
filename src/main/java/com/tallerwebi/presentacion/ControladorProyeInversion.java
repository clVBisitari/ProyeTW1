package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.ServicioProyectoInversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorProyeInversion {

    private ServicioProyectoInversion servicioProyectoInversion;

    @Autowired
    public ControladorProyeInversion(ServicioProyectoInversion proyectoInversion) {
        this.servicioProyectoInversion = proyectoInversion;
    }
    @RequestMapping(path = "/buscarProyeInversion", method = RequestMethod.GET)
    public ModelAndView buscarProyectoInversion(String nombre)
    {
        ModelMap model = new ModelMap();
        List<ProyectoInversion> proyectos = this.servicioProyectoInversion.buscarProyectoInversion(nombre);
        model.put("response", proyectos);
        return new ModelAndView("redirect:inversiones", model);
    }

}
