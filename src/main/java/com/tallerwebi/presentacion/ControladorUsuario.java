package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.interfaces.ServicioGestorGastos;
import com.tallerwebi.dominio.interfaces.ServicioProyectoInversion;
import com.tallerwebi.dominio.interfaces.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Transactional
@Controller
public class ControladorUsuario {

    private ServicioUsuario servicioUsuario;
    private ServicioGestorGastos servicioGastos;
    private ServicioProyectoInversion servicioProyectoInversion;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario, ServicioGestorGastos servicioGastos, ServicioProyectoInversion servProyectoInversion) {
        this.servicioUsuario = servicioUsuario;
        this.servicioGastos = servicioGastos;
        this.servicioProyectoInversion = servProyectoInversion;
    }

    @Transactional
    @RequestMapping(path = "/dashboard", method = RequestMethod.GET)
    public ModelAndView irADashboard(HttpServletRequest request) {

        // Redirigir si no esta loggeado
        if (!Usuario.isUserLoggedIn(request)) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener datos de usuario
        UsuarioDTO usuarioDto = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        Integer idUsuarioDto = usuarioDto.getId();

        BigDecimal totalGastosMesEnCurso = servicioGastos.obtenerGastosDelMes(idUsuarioDto);

        Integer cantidadGastosPorVencer = servicioGastos.actualizarCantidadServiciosPorVencerMesEnCurso(idUsuarioDto);
        List<ProyectoInversion> proyectosActivos = servicioProyectoInversion.getProyectosUsuario(idUsuarioDto);

        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuarioDto);

        modelo.addAttribute("totalGastosMesEnCurso", totalGastosMesEnCurso);
        modelo.addAttribute("cantidadGastosPorVencer", cantidadGastosPorVencer);
        modelo.addAttribute("proyectosActivos", proyectosActivos);
        modelo.addAttribute("ctdadProyesActivos", proyectosActivos.size());

        return new ModelAndView("dashboard", modelo);
    }

    @Transactional
    @RequestMapping(path = "/contactos", method = RequestMethod.GET)

    public ModelAndView irAContactos(HttpServletRequest request) {

        if (!Usuario.isUserLoggedIn(request)) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        List<Usuario> contactos = servicioUsuario.getContactos(usuarioDTO.getEmail());

        List<UsuarioDTO> contactosDTO = Usuario.mapToUsuarioDTOList(contactos);
        usuarioDTO.setContactos(contactosDTO);

        List<Usuario> contactosSugeridos = servicioUsuario.getContactosSugeridos(usuarioDTO.getId());
        List<UsuarioDTO> contactosSugeridosDTO = Usuario.mapToUsuarioDTOList(contactosSugeridos);

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

        Usuario usuario = servicioUsuario.getUsuarioById(id);

        boolean suspension = servicioUsuario.suspenderUsuario(motivo, id);

        if (suspension) {
            redirectAttributes.addFlashAttribute("mensaje", "Usuario suspendido");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "Error al suspender el usuario");
        }

        return "redirect:/contactos";
    }

    @RequestMapping(path = "/revertir-suspension/usuario/{id}", method = RequestMethod.POST)
    public String revertirSuspencion(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        Usuario usuario = servicioUsuario.getUsuarioById(id);

        boolean suspension = servicioUsuario.revertirSuspensionUsuario(id);

        if (suspension) {
            redirectAttributes.addFlashAttribute("mensaje", "Suspensión revertida");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "Error al revertir suspencion");
        }
        return "redirect:/vistaAdministrador";
    }

    @RequestMapping(path = "/agregar/contacto/{id}", method = RequestMethod.POST)
    public String agregarContacto(@PathVariable("id") Integer id, HttpServletRequest request) throws Exception {

        if (!Usuario.isUserLoggedIn(request)) {
            return "redirect:/login";
        }

        UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("USUARIO");
        Usuario usuarioQueGuarda = servicioUsuario.getUsuarioById(usuarioDTO.getId());
        Usuario usuarioAGuardar = servicioUsuario.getUsuarioById(id);
        servicioUsuario.agregarUsuarioAContactos(usuarioQueGuarda, usuarioAGuardar);

        return "redirect:/contactos";
    }

    @RequestMapping(path = "/eliminar/contacto/{id}", method = RequestMethod.POST)
    public String elmininarContacto(@PathVariable("id") Integer id, HttpServletRequest request) {

        if (!Usuario.isUserLoggedIn(request)) {
            return "redirect:/login";
        }

        UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("USUARIO");
        Usuario usuarioQueElimina = servicioUsuario.getUsuarioById(usuarioDTO.getId());
        Usuario usuarioAEliminar = servicioUsuario.getUsuarioById(id);

        servicioUsuario.eliminarUsuarioDeContactos(usuarioQueElimina, usuarioAEliminar);

        return "redirect:/contactos";
    }

    @RequestMapping(path = "/cambiarEstado/usuario/{id}", method = RequestMethod.POST)
    public String cambiarEstadoUsuario(@PathVariable("id") Integer id, HttpServletRequest request) throws Exception {

        if (!Usuario.isUserLoggedIn(request)) {
            return "redirect:/login";
        }
        Usuario usuario = servicioUsuario.getUsuarioById(id);
        servicioUsuario.cambiarEstadoUsuario(usuario);
        return "redirect:/vistaAdministrador";
    }


    @RequestMapping(path = "/buscar/contacto", method = RequestMethod.POST)
    public String buscarContacto(@RequestParam("nombre") String nombre, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (!Usuario.isUserLoggedIn(request)) {
            return "redirect:/login";
        }

        UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("USUARIO");
        List<Usuario> contactosEncontrados = servicioUsuario.buscarUsuarioPorNombre(nombre);
        List<UsuarioDTO> contactosEncontradosDTO = Usuario.mapToUsuarioDTOList(contactosEncontrados);

        if (contactosEncontradosDTO.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensajeContactoNoEncontrado", "contactos no encontrados con ese nombre");
        } else {
            redirectAttributes.addFlashAttribute("contactosEncontrados", contactosEncontradosDTO);

            List<UsuarioDTO> contactosActuales = usuarioDTO.getContactos();
            for (UsuarioDTO contactoEncontrado : contactosEncontradosDTO) {
                if (contactosActuales.contains(contactoEncontrado)) {
                    redirectAttributes.addFlashAttribute("mensajeContactoEnLista", "Ya son amigos");
                } else {
                    redirectAttributes.addFlashAttribute("mensajeContactoNuevo", "Todavía no son amigos");
                }
            }

        }
        return "redirect:/contactos";


    }

    @Transactional
    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView irAPerfil(HttpServletRequest request) {

        if (!Usuario.isUserLoggedIn(request)) {
            return new ModelAndView("redirect:/login");
        }

        UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("USUARIO");
        ModelMap modelo = new ModelMap();

        modelo.put("usuario", usuarioDTO);

        return new ModelAndView("perfil", modelo);
    }

    @Transactional
    @RequestMapping(path = "/saldo", method = RequestMethod.GET)
    public ModelAndView getSaldo(HttpServletRequest request) {

        return new ModelAndView("saldo");
    }

    @Transactional
    @RequestMapping(path = "/vistaAdministrador", method = RequestMethod.GET)
    public ModelAndView irAAdmin(HttpServletRequest request) {

        if (!Usuario.isUserLoggedIn(request) || !Usuario.isAdmin(request)) {
            return new ModelAndView("redirect:/login");
        }

        List<Usuario> usuariosSuspedidos = servicioUsuario.getUsuariosSuspedidos();
        //servicioUsuario.getProyectosSuspendidos(); ------
        List<UsuarioDTO> usuariosSuspendidos = Usuario.mapToUsuarioDTOList(usuariosSuspedidos);
        ModelMap modelo = new ModelMap();

        modelo.put("usuariosSuspendidos", usuariosSuspendidos);

        return new ModelAndView("vistaAdministrador", modelo);
    }
}