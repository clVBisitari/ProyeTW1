package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.interfaces.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
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


        if (!isUserLoggedIn(request)) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("USUARIO");
        Integer idUser = (Integer) request.getSession().getAttribute("idUsuario");
        model.put("usuario", usuario);
        model.addAttribute("idUsuario", idUser);
        return new ModelAndView("dashboard", model);
    }

    @Transactional
    @RequestMapping(path = "/contactos", method = RequestMethod.GET)

    public ModelAndView irAContactos(HttpServletRequest request) {

        if (!isUserLoggedIn(request)) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        List<Usuario> contactos = servicioUsuario.getContactos(usuarioDTO.getEmail());

        List<UsuarioDTO> contactosDTO = mapToUsuarioDTOList(contactos);
        usuarioDTO.setContactos(contactosDTO);

        List<Usuario> contactosSugeridos = servicioUsuario.getContactosSugeridos(usuarioDTO.getEmail());
        List<UsuarioDTO> contactosSugeridosDTO = mapToUsuarioDTOList(contactosSugeridos);

        model.put("usuarioActual", usuarioDTO);
        model.put("contactos", contactosDTO);
        model.put("contactosSugeridos", contactosSugeridosDTO);

        if (contactosDTO.isEmpty()) {
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

        if (!isUserLoggedIn(request)) {
            return "redirect:/login";
        }
        UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        Usuario usuarioQueGuarda = servicioUsuario.buscarUsuarioPorNombre(usuarioDTO.getNombre());

        Usuario usuarioAGuardar = servicioUsuario.buscarUsuarioPorNombre(nombre);

        servicioUsuario.agregarUsuarioAContactos(usuarioQueGuarda, usuarioAGuardar);

        return "redirect:/contactos";

    }

    @RequestMapping(path = "/buscar/contacto", method = RequestMethod.POST)
    public String buscarContacto(@RequestParam("nombre") String nombre, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (!isUserLoggedIn(request)) {
            return "redirect:/login";
        }
        UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("USUARIO");
        Usuario usuario = servicioUsuario.buscarUsuarioPorNombre(usuarioDTO.getNombre());
        Usuario contactoEncontrado = servicioUsuario.buscarUsuarioPorNombre(nombre);

        for (Usuario contacto : usuario.getContactos()) {
            if (contactoEncontrado != null && contacto == contactoEncontrado) {
                redirectAttributes.addFlashAttribute("contactoEncontrado", contactoEncontrado);
                redirectAttributes.addFlashAttribute("mensajeContactoEnLista", "ya son amigos");
                return "redirect:/contactos";
            }
        }
        if (contactoEncontrado != null && contactoEncontrado!=usuario) {
            redirectAttributes.addFlashAttribute("contactoEncontrado", contactoEncontrado);
            redirectAttributes.addFlashAttribute("mensajeContactoNuevo", "Todavia no son amigos");

        } else {
            redirectAttributes.addFlashAttribute("mensajeContactoNoEncontrado", "Contacto no econtrado con ese nombre");
        }


        return "redirect:/contactos";

    }

    private List<UsuarioDTO> mapToUsuarioDTOList(List<Usuario> usuarios) {
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNombre(usuario.getNombre());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setEnSuspencion(usuario.getEnSuspencion());
            usuariosDTO.add(usuarioDTO);
        }
        return usuariosDTO;
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("USUARIO") != null;
    }
}