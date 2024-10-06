package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorUsuario {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario){
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping("/dashboard")
    public ModelAndView irADashboard(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        HttpSession session = request.getSession(false); // Obtener la sesión actual, no crear una nueva
        if (session != null && session.getAttribute("USUARIO") != null) {
            Usuario usuario = (Usuario) session.getAttribute("USUARIO");
          //  logger.debug("Usuario en sesión: {}", usuario.getNombre());
            model.put("usuario", usuario); // Agregar el objeto Usuario al modelo

            return new ModelAndView("dashboard", model);
        } else {
        //    logger.debug("No hay usuario en la sesión, redirigiendo al login");
            return new ModelAndView("redirect:/login");
        }
    }

    @RequestMapping(path = "/contactos", method = RequestMethod.GET)

    public ModelAndView irAContactos(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        HttpSession session = request.getSession(false); // Obtener la sesión actual, no crear una nueva
        if (session != null && session.getAttribute("USUARIO") != null) {
            Usuario usuario = (Usuario) session.getAttribute("USUARIO");

            List<Usuario> contactos = servicioUsuario.getContactos(usuario.getEmail()); //mando email para usar metodos que ya estaban
            if (contactos != null && !contactos.isEmpty()) {
                model.put("contactos", contactos);
            } else {
                model.put("noHayContactos", "No hay contactos en tu lista");
            }

        } return new ModelAndView("contactos", model);
    }

}

   /* @RequestMapping(path = "/suspender/usuario=?", method = RequestMethod.POST)
    public ModelAndView suspenderUsuario(Usuario usuario) { /// servicioSuspenderUsuario

        if(usuario.getEnSuspencion()!=true){
            String mensaje = "el usuario ya se encuentra suspedido"
            ModelMap modeloUserSuspendido = new ModelMap();
            return new ModelAndView("redirect:/contactos");
        }
        return new ModelAndView("redirect:/contactos");

    }*/


