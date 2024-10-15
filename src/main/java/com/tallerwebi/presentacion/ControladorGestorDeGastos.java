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
    public ControladorGestorDeGastos(ServicioGestorDeGastosImpl servicioGestorDeGastosImpl) {
        this.servicioGestorDeGastosImpl = servicioGestorDeGastosImpl;
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
        return new ModelAndView("gasto", model);

    }


}