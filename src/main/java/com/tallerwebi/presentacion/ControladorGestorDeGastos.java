package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.interfaces.ServicioGestorGastos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
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
        return new ModelAndView("creacionGastos");
    }

    @RequestMapping(path = "/registrarGasto", method = RequestMethod.POST)
    public ModelAndView registrarGasto(@ModelAttribute("gasto") Gasto gasto, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuarioConectado = (Usuario) session.getAttribute("USUARIO");
        GestorDeGastos gestorConectado = usuarioConectado.getGestorDeGastos();
        gestorConectado.registrarGasto(gasto);
        this.servicioGestorGastos.guardarGestor(gestorConectado);
        return new ModelAndView("redirect:/dashboard");
    }

    @RequestMapping(path = "/dashboardGastos", method = RequestMethod.GET)
    public ModelAndView getDashboardGastos(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession(false);

     //   if (session != null && session.getAttribute("id") != null) {
    //        Long id = (Long) session.getAttribute("id");
          //  Double totalGastosDelMes = servicioGestorDeGastosImpl.actualizarTotalGastosDelMesEnCurso(id);
            model.put("totalGastosDelMes", 22);
    //    }
        return new ModelAndView("gastos", model);
    }

    @RequestMapping(path = "/vencimientos", method = RequestMethod.GET)
    public ModelAndView getVencimientos(HttpServletRequest request){
        return new ModelAndView("servicios");
    }
}