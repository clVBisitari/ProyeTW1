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

    @RequestMapping(path = "/dashboard", method = RequestMethod.GET)
    public ModelAndView actualizarGastosDelMesEnCursoYCantidadDeGastosPorVencer(HttpServletRequest request) {

        if (!isUserLoggedIn(request)) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");

        Long idUsuario = Long.valueOf(usuario.getId());

        List<Gasto> gastoList = servicioGestorGastos.obtenerTodosLosGastosDeUnGestor(Long.valueOf(idUsuario));

        GestorDeGastos gestorConectado = new GestorDeGastos();

        gestorConectado.setGastos(gastoList);

        Double totalGastosMesEnCurso = servicioGestorGastos.actualizarTotalGastosDelMesEnCursoPorId(gestorConectado.getId());

        Integer cantidadGastosPorVencer = servicioGestorGastos.actualizarCantidadServiciosPorVencerMesEnCurso(gestorConectado.getId());

        ModelMap modelo = new ModelMap();

        modelo.put("usuario", usuario);
        modelo.addAttribute("totalGastosMesEnCurso", totalGastosMesEnCurso);
        modelo.addAttribute("cantidadGastosPorVencer", cantidadGastosPorVencer);

        return new ModelAndView("dashboard", modelo);

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

    private boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        return session != null && isAttributeNotNull(session, "USUARIO");
    }

    private Boolean isAttributeNotNull(HttpSession session, String attributeName){
        return session.getAttribute(attributeName) != null;
    }

}