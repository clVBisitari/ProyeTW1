package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorGestorDeGastos {

    private ServicioGestorDeGastosImpl servicioGestorDeGastosImpl;

    @Autowired
    public ControladorGestorDeGastos(ServicioGestorDeGastosImpl servicioGestorDeGastos) {
        this.servicioGestorDeGastosImpl = servicioGestorDeGastos;
    }

    @RequestMapping(path = "/gasto", method = RequestMethod.GET)
    public ModelAndView registrarGasto() {

        ModelMap modelo = new ModelMap();
        modelo.put("gasto", new Gasto());
        return new ModelAndView("gasto", modelo);
    }

    @RequestMapping(path = "/registrarGasto", method = RequestMethod.POST)
    public ModelAndView registrarGasto(@ModelAttribute("gasto") Gasto gasto, HttpServletRequest request) {

        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuarioConectado = (Usuario) session.getAttribute("USUARIO");
        GestorDeGastos gestorConectado = usuarioConectado.getGestor();
        gestorConectado.registrarGasto(gasto);
        this.servicioGestorDeGastosImpl.guardarGestor(gestorConectado);
        return new ModelAndView("redirect:/dashboard");
    }

    @RequestMapping(path = "/dashboard", method = RequestMethod.GET)
    public ModelAndView actualizarGastosDelMesEnCursoYCantidadDeGastosPorVencer(HttpServletRequest request) {

        HttpSession session = request.getSession();
        UsuarioDTO usuarioConectado = (UsuarioDTO) session.getAttribute("USUARIO");
        Long usuarioConectadoId = (usuarioConectado.getId().longValue());
        if(servicioGestorDeGastosImpl.obtenerTodosLosGastosDeUnGestor(usuarioConectadoId)!=null){
            GestorDeGastos gestorConectado = new GestorDeGastos();
            gestorConectado.setGastos(servicioGestorDeGastosImpl.obtenerTodosLosGastosDeUnGestor(usuarioConectadoId));
            Double totalGastosMesEnCurso = servicioGestorDeGastosImpl.actualizarTotalGastosDelMesEnCursoPorId(gestorConectado.getId());
            int cantidadGastosPorVencer = servicioGestorDeGastosImpl.actualizarCantidadServiciosPorVencerMesEnCurso(gestorConectado.getId());
            ModelMap modelo = new ModelMap();
            modelo.addAttribute("totalGastosMesEnCurso", totalGastosMesEnCurso);
            modelo.addAttribute("cantidadGastosPorVencer", cantidadGastosPorVencer);
            return new ModelAndView("dashboard", modelo);
        }
        return null;
    }


    @RequestMapping("/dashboardGastos")
    public ModelAndView irADashboard(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        HttpSession session = request.getSession(false);

     //   if (session != null && session.getAttribute("id") != null) {
    //        Long id = (Long) session.getAttribute("id");
          //  Double totalGastosDelMes = servicioGestorDeGastosImpl.actualizarTotalGastosDelMesEnCurso(id);
            model.put("totalGastosDelMes", 22);

    //    }
        return new ModelAndView("redirect:/dashboard", model);

    }

}