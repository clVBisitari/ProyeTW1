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
import java.util.List;

@Controller
public class ControladorUsuario {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario){
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping("/dashboard")
    public ModelAndView irAdashboard() {

        ModelMap modelo = new ModelMap();
        modelo.put("UsuarioDTO", new UsuarioDTO());
        return new ModelAndView("dashboard", modelo);
    }

    @RequestMapping(path = "/contactos", method = RequestMethod.POST)

    public ModelAndView irAContactos(@ModelAttribute("UsuarioDTO") UsuarioDTO usuarioDTO) {

        ModelMap model = new ModelMap();

        List<Usuario> contactos = servicioUsuario.getContactos();
        if (contactos!=null){
            model.put("contactos",contactos);

        } else {
            model.put("noHayContactos", "No hay contactos en tu lista");
        }

        return new ModelAndView("contactos",model);
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
}

