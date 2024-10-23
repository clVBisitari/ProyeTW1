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

        // Obtener contactos actuales del usuario
        List<Usuario> contactos = servicioUsuario.getContactos(usuarioDTO.getEmail());
        List<UsuarioDTO> contactosDTO = mapToUsuarioDTOList(contactos);
        usuarioDTO.setContactos(contactosDTO);

        // Obtener contactos sugeridos
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


    @RequestMapping(path = "/suspender/usuario/{id}", method = RequestMethod.POST)
    public String suspenderUsuario(@PathVariable("id") Integer id, @RequestParam("motivo") String motivo, RedirectAttributes redirectAttributes) {


        Usuario usuario = servicioUsuario.buscarUsuarioPorId(id);

        boolean suspension = servicioUsuario.suspenderUsuario(motivo, id);

        if (suspension) {
            redirectAttributes.addFlashAttribute("mensaje", "Usuario suspendido");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "Error al suspender el usuario");
        }

        return "redirect:/contactos";
    }

    @RequestMapping(path = "/revertir-suspencion/usuario/{id}", method = RequestMethod.POST)
    public String revertirSuspencion(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes) {

        boolean suspension = servicioUsuario.revertirSuspensionUsuario(id);

        if (suspension) {
            redirectAttributes.addFlashAttribute("mensaje", "Suspensión revertida");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "Error al revertir suspencion");
        }

        return "redirect:/contactos";
    }


    @RequestMapping(path = "/agregar/contacto/{id}", method = RequestMethod.POST)
    public String agregarContacto(@PathVariable("id") Integer id, HttpServletRequest request) {

            UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

            if (usuarioDTO == null) {
                throw new IllegalArgumentException("El usuario no está autenticado");
            }

            Usuario usuarioQueGuarda = servicioUsuario.buscarUsuarioPorId(usuarioDTO.getId());
            if (usuarioQueGuarda == null) {
                throw new IllegalArgumentException("El usuario que intenta guardar no existe");
            }

            Usuario usuarioAGuardar = servicioUsuario.buscarUsuarioPorId(id);
            if (usuarioAGuardar == null) {
                throw new IllegalArgumentException("El usuario a agregar no puede ser null");
            }

            servicioUsuario.agregarUsuarioAContactos(usuarioQueGuarda, usuarioAGuardar);
            return "redirect:/contactos";
        }

    @RequestMapping(path = "/buscar/contacto", method = RequestMethod.POST)
    public String buscarContacto(@RequestParam("nombre") String nombre, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!isUserLoggedIn(request)) {
            return "redirect:/login";
        }

        UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        // Verifica que el nombre que se busca no sea el mismo que el del usuario logueado
        if (nombre.equals(usuarioDTO.getNombre())) {
            redirectAttributes.addFlashAttribute("mensajeContactoNoEncontrado", "No puedes buscar tu propio contacto.");
            return "redirect:/contactos";
        }

        List<Usuario> contactosEncontrados = servicioUsuario.buscarUsuario(nombre);
        List<UsuarioDTO>contactosEncontradosDTO = mapToUsuarioDTOList(contactosEncontrados);

        if (contactosEncontradosDTO.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensajeContactoNoEncontrado", "Contacto no encontrado con ese nombre");
        } else {
            redirectAttributes.addFlashAttribute("contactosEncontrados", contactosEncontradosDTO);
            Usuario usuario = servicioUsuario.buscarUsuarioPorNombre(usuarioDTO.getNombre());

            if (usuario != null && usuario.getContactos() != null) {
                List<Usuario> contactosActuales = usuario.getContactos();
                for (Usuario contactoEncontrado : contactosEncontrados) {
                    if (contactosActuales.contains(contactoEncontrado)) {
                        redirectAttributes.addFlashAttribute("mensajeContactoEnLista", "Ya son amigos");
                    } else {
                        redirectAttributes.addFlashAttribute("mensajeContactoNuevo", "Todavía no son amigos");
                    }
                }
            }
        }

        return "redirect:/contactos";
    }
    private List<UsuarioDTO> mapToUsuarioDTOList(List<Usuario> usuarios) {
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setNombre(usuario.getNombre());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setEnSuspension(usuario.getEnSuspension());
            usuariosDTO.add(usuarioDTO);
        }
        return usuariosDTO;
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("USUARIO") != null;
    }
}