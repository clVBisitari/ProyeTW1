package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.interfaces.ServicioGestorGastos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

        ModelMap model = new ModelMap();

        model.put("gastoDTO", new GastoDTO());

        return new ModelAndView("creacionGastos", model);
    }

    @RequestMapping(path = "/registrarGasto", method = RequestMethod.POST)
    public ModelAndView registrarGasto(GastoDTO gastoDTO, HttpServletRequest request) {

        ModelMap model = new ModelMap();

        // Buscar datos de usuario
        UsuarioDTO usuarioConectado = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        // Buscar gestor de usuarios
        GestorDeGastos gestorConectado = servicioGestorGastos.buscarPorUsuario(usuarioConectado.getId());

        // Convertir gastos y asignar gestor
        Gasto gasto = GastoDTO.convertirDTOaGasto(gastoDTO);
        gasto.setGestor(gestorConectado);

        // Guardar gasto
        this.servicioGestorGastos.guardarGasto(gasto);

        return new ModelAndView("redirect:/dashboardGastos");
    }

    @RequestMapping(path = "/dashboardGastos", method = RequestMethod.GET)
    public ModelAndView getDashboardGastos(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        UsuarioDTO usuarioConectado = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        GestorDeGastos gestorConectado = servicioGestorGastos.buscarPorUsuario(usuarioConectado.getId());

        List<GastoDTO> gastos = servicioGestorGastos.obtenerTodosLosGastosDeUnGestor(gestorConectado.getId());

        model.addAttribute("gastos", gastos);

        return new ModelAndView("gastos", model);
    }

    @RequestMapping(path = "/vencimientos", method = RequestMethod.GET)
    public ModelAndView getVencimientos(HttpServletRequest request){
        return new ModelAndView("servicios");
    }
}