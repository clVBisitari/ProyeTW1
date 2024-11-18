package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.interfaces.ServicioLogin;
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
import javax.transaction.Transactional;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin() {

        return new ModelAndView("login", new ModelMap("datosLogin", new DatosLogin()));
    }

    @Transactional
    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());

        if (usuarioBuscado != null) {

            HttpSession session = request.getSession();
            UsuarioDTO usuarioDTO = UsuarioDTO.convertUsuarioToDTO(usuarioBuscado);
            session.setAttribute("idUsuario", usuarioBuscado.getId());
            session.setAttribute("esAdmin", usuarioBuscado.getEsAdmin());
            session.setAttribute("saldo", usuarioBuscado.getSaldo());
          //  model.addAttribute("idUsuario", usuarioBuscado.getId());
            session.setAttribute("USUARIO", usuarioDTO);
            model.addAttribute("USUARIO", usuarioDTO);
            return new ModelAndView("redirect:/dashboard");

        } else {
            return new ModelAndView("login", new ModelMap("error", "Usuario o clave incorrecta"));
        }
}


@RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        try {
            servicioLogin.registrar(usuario);

        } catch (UsuarioExistente e) {
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e) {
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("nuevo-usuario", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome() {
        return new ModelAndView("home");
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/cerrarSesion", method = RequestMethod.GET)
    public ModelAndView cerrarSesion(HttpServletRequest request) {

        HttpSession session = request.getSession(false); // Obtener la sesión actual, no crear una nueva

        if (session != null && session.getAttribute("USUARIO") != null) {
            session.invalidate();
        }
        return new ModelAndView("redirect:/login");
    }
}




