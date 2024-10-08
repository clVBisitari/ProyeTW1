package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Transactional
@Controller
public class ControladorUsuario {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }
    @Transactional
    @RequestMapping("/dashboard")
    public ModelAndView irADashboard(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("USUARIO") != null) {
            Usuario usuario = (Usuario) session.getAttribute("USUARIO");
            model.put("usuario", usuario);

            return new ModelAndView("dashboard", model);
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

    @Transactional
    @RequestMapping(path = "/contactos", method = RequestMethod.GET)

    public ModelAndView irAContactos(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("USUARIO") != null) {
            Usuario usuario = (Usuario) session.getAttribute("USUARIO");

            List<Usuario> contactos = servicioUsuario.getContactos(usuario.getEmail());
            if (contactos != null && !contactos.isEmpty()) {
                model.put("usuarioActual", session.getAttribute("USUARIO"));
                model.put("contactos", contactos);

            } else {
                model.put("noHayContactos", "No hay contactos en tu lista");
            }

        }
        return new ModelAndView("contactos", model);
    }


    @RequestMapping(path = "/suspender/usuario/{nombre}", method = RequestMethod.POST)
    public String suspenderUsuario(@PathVariable("nombre") String nombre, @RequestParam("motivo") String motivo, RedirectAttributes redirectAttributes) {
        Usuario usuario = servicioUsuario.buscarUsuarioPorNombre(nombre);

        servicioUsuario.suspenderUsuario(motivo, usuario.getId());


        if (usuario.getEnSuspencion()) {
            redirectAttributes.addFlashAttribute("mensaje", "Usuario suspendido");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "Error al suspender el usuario");
        }

        return "redirect:/contactos";
    }


}