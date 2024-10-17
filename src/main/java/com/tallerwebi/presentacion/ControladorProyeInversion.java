package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.interfaces.ServicioProyectoInversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorProyeInversion {

    private ServicioProyectoInversion servicioProyectoInversion;

    @Autowired
    public ControladorProyeInversion(ServicioProyectoInversion proyectoInversion) {
        this.servicioProyectoInversion = proyectoInversion;
    }


    @RequestMapping(path= "/inversiones", method = RequestMethod.GET)
    public ModelAndView getListaProyectosPorUsuario(HttpServletRequest request){
        ModelMap model = new ModelMap();
        Integer userId = (Integer) request.getSession().getAttribute("idUsuario");
        List<ProyectoInversion> proyectosResult = this.servicioProyectoInversion.getProyectosUsuario(userId);
        model.put("proyectos", proyectosResult);
        return new ModelAndView("inversiones", model);
    }

    @RequestMapping(path = "/buscarProyeInversion", method = RequestMethod.GET)
    public ModelAndView buscarProyectoInversion(@RequestParam("nombre") String nombre)
    {
        ModelMap model = new ModelMap();
        List<ProyectoInversion> proyectos = this.servicioProyectoInversion.buscarProyectoInversion(nombre);
        model.put("response", proyectos);
        return new ModelAndView("redirect:inversiones", model);
    }


    @RequestMapping(path = "/crearProyeInversion", method = RequestMethod.GET)
    public ModelAndView crearProyectoInversion(){
        ModelMap model = new ModelMap();
        return new ModelAndView("creacionInversiones", model);
    }

    @RequestMapping(path = "/guardarProyeInversion", method = RequestMethod.POST)
    public ModelAndView guardarProyectoInversion(@ModelAttribute ProyectoInversion proyeInversion){
        ModelMap model = new ModelMap();
        Long proyectoResponse = this.servicioProyectoInversion.guardarProyectoInversion(proyeInversion);
        model.put("response", proyectoResponse);
        return new ModelAndView("redirect:inversiones", model);
    }

    @RequestMapping(path = "/editarProyeInversion", method = RequestMethod.PUT)
    public ModelAndView editarProyectoInversion(@ModelAttribute ProyectoInversion proyeInversion){
        ModelMap model = new ModelMap();
        ProyectoInversion proyectoResponse = this.servicioProyectoInversion.editarProyectoInversion(proyeInversion);
        model.put("response", proyectoResponse);
        return new ModelAndView("redirect:inversiones", model);
    }

    @RequestMapping(path = "/borrarProyeInversion", method = RequestMethod.DELETE)
    public ModelAndView borrarProyectoInversion(@RequestParam Long idProyeInversion){
        ModelMap model = new ModelMap();
        boolean deleteResponse = this.servicioProyectoInversion.borrarProyectoInversion(idProyeInversion);
        model.put("response", deleteResponse);
        return new ModelAndView("redirect:inversiones", model);
    }

    @RequestMapping(path = "/reportarProyecto", method = RequestMethod.POST)
    public ModelAndView reportarProyecto(@RequestParam Long idProyeInversion){
        ModelMap model = new ModelMap();
        boolean reportarFueExitoso = this.servicioProyectoInversion.reportarProyecto(idProyeInversion);
        model.put("response", reportarFueExitoso);
        return new ModelAndView("redirect:inversiones", model);
    }

    @RequestMapping(path = "/suspenderProyecto", method = RequestMethod.POST)
    public ModelAndView suspenderProyecto(@RequestParam Long idProyeInversion){
        ModelMap model = new ModelMap();
        boolean suspensionExitosa = this.servicioProyectoInversion.suspenderProyecto(idProyeInversion);
        model.put("response", suspensionExitosa);
        return new ModelAndView("redirect:inversiones", model);
    }

    @RequestMapping(path="/listarPublicacionesSuspendidas", method=RequestMethod.GET)
    public ModelAndView listarPublicacionesSuspendidas(@RequestParam Integer idUsuario){
        ModelMap model = new ModelMap();
        List<ProyectoInversion> proyectos = this.servicioProyectoInversion.listarPublicacionesSupendidas(idUsuario);
        model.put("response", proyectos);
        return new ModelAndView("redirect:inversiones", model);
    }
}
