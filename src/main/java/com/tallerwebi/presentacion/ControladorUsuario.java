package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.dominio.GestorDeGastos;
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
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
public class ControladorUsuario {

    private ServicioUsuario servicioUsuario;
    private ServicioGestorGastos servicioGastos;
    private ServicioProyectoInversion servicioProyectoInversion;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario, ServicioGestorGastos servicioGastos, ServicioProyectoInversion servicioProyeInversion) {
        this.servicioUsuario = servicioUsuario;
        this.servicioGastos = servicioGastos;
        this.servicioProyectoInversion = servicioProyectoInversion;
    }

    @Transactional
    @RequestMapping(path ="/dashboard", method = RequestMethod.GET)
    public ModelAndView irADashboard(HttpServletRequest request) {

        if (!Usuario.isUserLoggedIn(request)) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");

        Long idUsuario = Long.valueOf(usuario.getId());

        List<Gasto> gastoList = servicioGastos.obtenerTodosLosGastosDeUnGestor(Long.valueOf(idUsuario));

        GestorDeGastos gestorConectado = new GestorDeGastos();

        gestorConectado.setGastos(gastoList);

        Double totalGastosMesEnCurso = servicioGastos.actualizarTotalGastosDelMesEnCursoPorId(gestorConectado.getId());

        Integer cantidadGastosPorVencer = servicioGastos.actualizarCantidadServiciosPorVencerMesEnCurso(gestorConectado.getId());

        ModelMap modelo = new ModelMap();

        modelo.put("usuario", usuario);
        modelo.addAttribute("totalGastosMesEnCurso", totalGastosMesEnCurso);
        modelo.addAttribute("cantidadGastosPorVencer", cantidadGastosPorVencer);

        return new ModelAndView("dashboard", modelo);
    }

    @Transactional
    @RequestMapping(path = "/contactos", method = RequestMethod.GET)

    public ModelAndView irAContactos(HttpServletRequest request) {

        if (!Usuario.isUserLoggedIn(request)) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        UsuarioDTO usuarioDTO = UsuarioDTO.convertUsuarioToDTO((Usuario)request.getSession().getAttribute("USUARIO"));

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
    public String revertirSuspencion(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes) {

        Usuario usuario = servicioUsuario.getUsuarioById(id);
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


        if (!Usuario.isUserLoggedIn(request)) {
            return "redirect:/login";
        }
        UsuarioDTO usuarioDTO = UsuarioDTO.convertUsuarioToDTO((Usuario)request.getSession().getAttribute("USUARIO"));
        Usuario usuarioQueGuarda = servicioUsuario.getUsuarioById(usuarioDTO.getId());
        Usuario usuarioAGuardar = servicioUsuario.getUsuarioById(id);
        servicioUsuario.agregarUsuarioAContactos(usuarioQueGuarda, usuarioAGuardar);

        return "redirect:/contactos";
    }

    @RequestMapping(path = "/buscar/contacto", method = RequestMethod.POST)
    public String buscarContacto(@RequestParam("nombre") String nombre, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (!Usuario.isUserLoggedIn(request)) {
            return "redirect:/login";
        }

        UsuarioDTO usuarioDTO = UsuarioDTO.convertUsuarioToDTO((Usuario)request.getSession().getAttribute("USUARIO"));
        Usuario usuario = servicioUsuario.getUsuarioById(usuarioDTO.getId());
        List<Usuario> contactosEncontrados = servicioUsuario.buscarUsuarioPorNombre(nombre);
        List<UsuarioDTO>contactosEncontradosDTO = Usuario.mapToUsuarioDTOList(contactosEncontrados);

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
    @RequestMapping(path = "/saldo", method = RequestMethod.GET)
    public ModelAndView getSaldo(HttpServletRequest request) {

        return new ModelAndView("saldo");
    }
}