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

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("USUARIO") == null) {
            return new ModelAndView("redirect:/login");
        }

        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("USUARIO");
        model.put("usuario", usuario);

        return new ModelAndView("dashboard", model);
    }

    @Transactional
    @RequestMapping(path = "/contactos", method = RequestMethod.GET)

    public ModelAndView irAContactos(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        HttpSession session = request.getSession(false);


        if (session == null || session.getAttribute("USUARIO") == null) {
            return new ModelAndView("redirect:/login");
        }

        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("USUARIO");

        List<Usuario> contactos = servicioUsuario.getContactos(usuarioDTO.getEmail());

        ArrayList<UsuarioDTO> contactosDTO = new ArrayList<>();

        for (Usuario contacto : contactos) {
            UsuarioDTO contactoDTO = new UsuarioDTO();
            contactoDTO.setNombre(contacto.getNombre());
            contactoDTO.setEmail(contacto.getEmail());
            contactoDTO.setEnSuspencion(contacto.getEnSuspencion());
            contactosDTO.add(contactoDTO);

        }
        usuarioDTO.setContactos(contactosDTO);

        List<Usuario> contactosSugeridos = servicioUsuario.getContactosSugeridos(usuarioDTO.getEmail());
        ArrayList<UsuarioDTO> contactosSugeridosDTO = new ArrayList<>();

        for (Usuario contactoSugerido : contactosSugeridos) {
            UsuarioDTO contactoSugeridoDTO = new UsuarioDTO();
            contactoSugeridoDTO.setNombre(contactoSugerido.getNombre());
            contactoSugeridoDTO.setEmail(contactoSugerido.getEmail());
            contactoSugeridoDTO.setEnSuspencion(contactoSugerido.getEnSuspencion());
            contactosSugeridosDTO.add(contactoSugeridoDTO);
        }

        if (contactosDTO != null && !contactosDTO.isEmpty()) {
            model.put("usuarioActual", session.getAttribute("USUARIO"));
            model.put("contactos", contactosDTO);
            model.put("contactosSugeridos", contactosSugeridosDTO);

        } else if (contactosDTO == null || contactosDTO.isEmpty()) {
            model.put("noHayContactos", "No hay contactos en tu lista");
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

    @RequestMapping(path = "/revertir-suspencion/usuario/{nombre}", method = RequestMethod.POST)
    public String revertirSuspencion(@PathVariable("nombre") String nombre) {

        Usuario usuario = servicioUsuario.buscarUsuarioPorNombre(nombre);

        servicioUsuario.revertirSuspensionUsuario(usuario.getId());

        return "redirect:/contactos";
    }


    @RequestMapping(path = "/agregar/contacto/{nombre}", method = RequestMethod.POST)
    public String agregarContacto(@PathVariable("nombre") String nombre, HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("USUARIO");

        Usuario usuarioQueGuarda = servicioUsuario.buscarUsuarioPorNombre(usuarioDTO.getNombre());

        Usuario usuarioAGuardar = servicioUsuario.buscarUsuarioPorNombre(nombre);

        servicioUsuario.agregarUsuarioAContactos(usuarioQueGuarda, usuarioAGuardar);

        return "redirect:/contactos";

    }

    @RequestMapping(path = "/buscar/contacto", method = RequestMethod.POST)
    public String buscarContacto(@RequestParam("nombre") String nombre, RedirectAttributes redirectAttributes) {


        Usuario contactoEncontrado = servicioUsuario.buscarUsuarioPorNombre(nombre);

        if (contactoEncontrado != null) {
            redirectAttributes.addFlashAttribute("contactoEncontrado", contactoEncontrado);
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "No se encontro nungun contacto con ese nombre");
        }

        return "redirect:/contactos";

    }

}