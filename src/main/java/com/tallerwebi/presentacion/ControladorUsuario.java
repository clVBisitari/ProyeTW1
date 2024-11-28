package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.Saldo;
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
        ModelMap modelo = new ModelMap();

        // Redirigir si no esta loggeado
        if (!Usuario.isUserLoggedIn(request)) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener datos de usuario

        Integer idUsuarioDto = (Integer) request.getSession().getAttribute("idUsuario");
        Usuario usuario = servicioUsuario.getUsuarioById(idUsuarioDto);

        UsuarioDTO usuarioDtoParaSerGuardado = UsuarioDTO.convertUsuarioToDTO(usuario);

        request.getSession().setAttribute("USUARIO", usuarioDtoParaSerGuardado);

        BigDecimal totalGastosMesEnCurso = servicioGastos.obtenerGastosDelMes(idUsuarioDto);

        Integer cantidadGastosPorVencer = servicioGastos.actualizarCantidadServiciosPorVencerMesEnCurso(idUsuarioDto);
        List<ProyectoInversion> proyectosActivos = servicioProyectoInversion.getProyectosUsuario(idUsuarioDto);
        try {
            List<ProyeInversionDTO> proyesRecomendados = servicioUsuario.obtenerProyectosRecomendados(idUsuarioDto);

            modelo.put("proyesRecomendados", proyesRecomendados);
        } catch (Exception e) {
            return new ModelAndView("redirect:/login");
        }
        List<Gasto> gastos = servicioGastos.obtenerTodosLosGastosDeUnGestor(idUsuarioDto);

        modelo.put("usuario", usuarioDtoParaSerGuardado);

        modelo.addAttribute("totalGastosMesEnCurso", totalGastosMesEnCurso);
        modelo.addAttribute("cantidadGastosPorVencer", cantidadGastosPorVencer);
        modelo.addAttribute("proyectosActivos", proyectosActivos);
        modelo.addAttribute("ctdadProyesActivos", proyectosActivos.size());
        modelo.addAttribute("gastos", gastos);
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
    public ModelAndView irAPerfil(HttpServletRequest request) throws Exception {

        if (!Usuario.isUserLoggedIn(request)) {
            return new ModelAndView("redirect:/login");
        }

        UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        ModelMap modelo = new ModelMap();

        modelo.put("usuario", usuarioDTO);

        return new ModelAndView("perfil", modelo);
    }
    @Transactional
    @RequestMapping(path = "/editarPerfil", method = RequestMethod.GET)
    public ModelAndView irAEditar(HttpServletRequest request) {

        if (!Usuario.isUserLoggedIn(request)) {
            return new ModelAndView("redirect:/login");

        }
        UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("USUARIO");

        ModelMap modelo = new ModelMap();

        modelo.put("usuario", usuarioDTO);

        return new ModelAndView("editarPerfil", modelo);
    }
    @Transactional
    @RequestMapping(path = "/editarPerfil/usuario", method = RequestMethod.POST)
    public String editarPerfil(@RequestParam("usuarioId") Integer usuarioId,
                               @ModelAttribute("usuario") UsuarioDTO usuarioDTO,
                               HttpServletRequest request) throws Exception {


        if (!Usuario.isUserLoggedIn(request)) {
            return "redirect:/login";
        }

        if (usuarioId == null) {

            return "redirect:/login";
        }
        if (usuarioDTO.getNombre() == null || usuarioDTO.getApellido() == null) {
            return "editarPerfil";
        }
        Usuario usuario = servicioUsuario.getUsuarioById(usuarioId);

        if (usuario == null) {
            return "redirect:/login";
        }


        usuario.setSaldo(usuarioDTO.getSaldo());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setDni(usuarioDTO.getDni());
                servicioUsuario.actualizarDatos(usuario);

        return "redirect:/dashboard";
    }

    @Transactional
    @RequestMapping(path = "/saldo", method = RequestMethod.GET)
    public ModelAndView getSaldo(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        UsuarioDTO usuario = (UsuarioDTO)request.getSession().getAttribute("USUARIO");
        Integer idUsuario = usuario.getId();
        List<Saldo> saldosUsuario = servicioUsuario.getHistorialSaldoByIdUsuario(idUsuario);
        model.addAttribute("saldos", saldosUsuario);
        model.addAttribute("usuario", usuario);

        return new ModelAndView("saldo", model);
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
    @RequestMapping(path="/cargarSaldo", method = RequestMethod.POST)
    public ModelAndView cargarSaldo(@RequestParam("importe") Integer monto, HttpServletRequest request) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("USUARIO");
        Integer idUsuario = usuario.getId();
        try {
            var saldoCargado = this.servicioUsuario.cargarSaldo(new BigDecimal(monto), idUsuario);
            if(saldoCargado == null || saldoCargado == false) {
                return new ModelAndView("redirect:/login");
            }
        } catch (Exception e) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        return new ModelAndView("redirect:/dashboard", model);
    }
}