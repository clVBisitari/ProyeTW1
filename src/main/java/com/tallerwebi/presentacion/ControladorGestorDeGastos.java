package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.interfaces.ServicioGestorGastos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorGestorDeGastos {

    private ServicioGestorGastos servicioGestorGastos;

    @Autowired
    public ControladorGestorDeGastos(ServicioGestorGastos servicioGestorDeGastos) {
        this.servicioGestorGastos = servicioGestorDeGastos;
    }

    @RequestMapping(path = "/gasto", method = RequestMethod.GET)
    public ModelAndView registrarGasto() {

        ModelMap modelo = new ModelMap();
        modelo.put("gasto", new Gasto());
        return new ModelAndView("gasto", modelo);
    }

    @RequestMapping(path = "/crearGasto", method = RequestMethod.GET)
    public ModelAndView crearGasto(HttpServletRequest request) {

        return new ModelAndView("creacionGastos", new ModelMap("gasto", new Gasto()));
    }

    @RequestMapping(path = "/registrarGasto", method = RequestMethod.POST)
    public ModelAndView registrarGasto(GastoDTO gastoDTO, HttpServletRequest request) {

        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        UsuarioDTO usuarioConectado = (UsuarioDTO) session.getAttribute("USUARIO");
        boolean guardadoExitoso = this.servicioGestorGastos.guardarGasto(usuarioConectado.getId(), gastoDTO);
        if(guardadoExitoso){model.addAttribute("gastoGuardado", true);}
        return new ModelAndView("redirect:/dashboard", model);
    }

    @RequestMapping(path = "/dashboardGastos", method = RequestMethod.GET)
    public ModelAndView getDashboardGastos(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        UsuarioDTO usuarioConectado = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        List<Gasto> gastos = servicioGestorGastos.obtenerTodosLosGastosDeUnGestor(usuarioConectado.getId());

        model.addAttribute("gastos", gastos);

        return new ModelAndView("gastos", model);
    }

    @RequestMapping(path= "/eliminarGasto/{id}", method = RequestMethod.GET)
    public ModelAndView eliminarGasto(HttpServletRequest request, @PathVariable("id") Integer id){

        // Redirigir si no esta loggeado
        if (!Usuario.isUserLoggedIn(request)) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();

        UsuarioDTO usuarioConectado = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        this.servicioGestorGastos.eliminarGasto(id.longValue());

        List<Gasto> gastos = servicioGestorGastos.obtenerTodosLosGastosDeUnGestor(usuarioConectado.getId());

        model.addAttribute("gastos", gastos);

        return new ModelAndView("gastos", model);
    }

    @RequestMapping(path = "/vencimientos", method = RequestMethod.GET)
    public ModelAndView getVencimientos(HttpServletRequest request){

        ModelMap model = new ModelMap();

        UsuarioDTO usuarioConectado = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        List<Gasto> gastos = servicioGestorGastos.obtenerLosGastosNoVencidosDeUnUsuario(usuarioConectado.getId());

        model.addAttribute("gastos", gastos);

        return new ModelAndView("gastos", model);
    }

    @RequestMapping(path = "/gastosMesEnCurso", method = RequestMethod.GET)
    public ModelAndView getGastosMesEnCurso(HttpServletRequest request){

        ModelMap model = new ModelMap();

        UsuarioDTO usuarioConectado = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        List<Gasto> gastos = servicioGestorGastos.obtenerLosGastosDelMesEnCursoDeUnUsuario(usuarioConectado.getId());

        model.addAttribute("gastos", gastos);

        return new ModelAndView("gastos", model);
    }
}