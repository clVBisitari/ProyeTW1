package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.interfaces.ServicioGestorGastos;
import com.tallerwebi.dominio.interfaces.ServicioProyectoInversion;
import com.tallerwebi.dominio.interfaces.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

public class ControladorUsuarioTest {

    private ServicioUsuario servicioUsuarioMock;
    private ServicioGestorGastos servicioGestorGastosMock;
    private ServicioProyectoInversion servicioProyectionInversionMock;
    private UsuarioDTO usuarioDTOMock;
    private ControladorUsuario controladorUsuario;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private RedirectAttributes redirectAttributesMock;

    @BeforeEach
    public void init() {
        redirectAttributesMock = mock(RedirectAttributes.class);
        usuarioDTOMock = mock(UsuarioDTO.class);
        when(usuarioDTOMock.getEmail()).thenReturn("test@unlam.edu.ar");

        servicioUsuarioMock = mock(ServicioUsuario.class);
        servicioGestorGastosMock = mock(ServicioGestorGastos.class);
        servicioProyectionInversionMock = mock(ServicioProyectoInversion.class);
        controladorUsuario = new ControladorUsuario(servicioUsuarioMock, servicioGestorGastosMock, servicioProyectionInversionMock);

        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoAlIrAcontactosYTenerPuedeVerLaListaDeUsuariosContacto() {


        when(requestMock.getSession(false)).thenReturn(sessionMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioDTOMock);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(2);

        List<Usuario> contactos = new ArrayList<>();
        contactos.add(new Usuario());
        contactos.add(new Usuario());
        contactos.add(new Usuario());

        when(servicioUsuarioMock.getContactos(usuarioDTOMock.getEmail())).thenReturn(contactos);

        ModelAndView modelAndView = controladorUsuario.irAContactos(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("contactos"));
        assertThat(modelAndView.getModel().get("contactos"), is(notNullValue()));

    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoAlCargarSaldoCambiaElValor() {

        when(requestMock.getSession(false)).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioDTOMock);


    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoAlIrAcontactosYNoTenerPuedeVerUnMensajeDeAviso() {

        when(requestMock.getSession(false)).thenReturn(sessionMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioDTOMock);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(2);
        List<Usuario> contactos = new ArrayList<>();

        when(servicioUsuarioMock.getContactos(usuarioDTOMock.getEmail())).thenReturn(contactos);

        ModelAndView modelAndView = controladorUsuario.irAContactos(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("contactos"));
        assertThat(modelAndView.getModel().get("noHayContactos"), is(equalTo("No hay contactos en tu lista")));

    }

    @Test
    public void dadoQueNoExisteUnUsuarioLogueadoAlIrAcontactosVuelveALaPaginaDeLogueo() {

        when(requestMock.getSession(false)).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(null);

        ModelAndView modelAndView = controladorUsuario.irAContactos(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoAlIrADashBoardEntoncesSeRenderizaLaVistaCorrectamenteConLosDatos() {
        when(requestMock.getSession(false)).thenReturn(sessionMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioDTOMock);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(2);

        ModelAndView mav = controladorUsuario.irADashboard(requestMock);

        assertThat(mav.getViewName(), equalToIgnoringCase("dashboard"));
        assertThat(mav.getModel().get("usuario"), equalTo(usuarioDTOMock));
    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoYEsAdminSePuedeSuspenderAOtro() {
        Usuario usuarioASuspender = new Usuario();
        usuarioASuspender.setId(16);
        usuarioASuspender.setNombre("Alexi");
        usuarioASuspender.setEnSuspension(false);
        String motivo = "mal comportamiento";

        when(servicioUsuarioMock.buscarUsuarioPorId(16)).thenReturn(usuarioASuspender);
        when(servicioUsuarioMock.suspenderUsuario(eq("mal comportamiento"), eq(16))).thenReturn(true);

        String resultado = controladorUsuario.suspenderUsuario(usuarioASuspender.getId(), motivo, redirectAttributesMock);
        assertThat(resultado, is("redirect:/contactos"));
    }


    @Test
    public void dadoQueSeRevierteLaSuspensionSeMuestraMensajeExitoso() {
        Usuario usuarioASuspender = new Usuario();
        usuarioASuspender.setId(16);
        when(servicioUsuarioMock.getUsuarioById(16)).thenReturn(usuarioASuspender);
        when(servicioUsuarioMock.revertirSuspensionUsuario(usuarioASuspender.getId())).thenReturn(true);

        String resultado = controladorUsuario.revertirSuspencion(usuarioASuspender.getId(), redirectAttributesMock);

        verify(servicioUsuarioMock).revertirSuspensionUsuario(usuarioASuspender.getId());

        verify(redirectAttributesMock).addFlashAttribute("mensaje", "Suspensión revertida");

        assertThat(resultado, is("redirect:/contactos"));
    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoPuedeAgregarOtroUsuarioQueNoEsteEnSuListaDeContactos() {

        // Mock de la sesión y los atributos de la sesión
        when(requestMock.getSession(false)).thenReturn(sessionMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioDTOMock);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(1);


        // Simulación de que el usuario está logueado
        when(usuarioDTOMock.getId()).thenReturn(1);
        when(Usuario.isUserLoggedIn(requestMock)).thenReturn(true);

        // Usuarios a ser guardados
        Usuario usuarioQueGuarda = new Usuario();
        usuarioQueGuarda.setId(1);

        Usuario usuarioAGuardar = new Usuario();
        usuarioAGuardar.setId(2);

        // Mock de servicios
        when(servicioUsuarioMock.getUsuarioById(usuarioDTOMock.getId())).thenReturn(usuarioQueGuarda);
        when(servicioUsuarioMock.getUsuarioById(16)).thenReturn(usuarioAGuardar);
        when(servicioUsuarioMock.agregarUsuarioAContactos(usuarioQueGuarda, usuarioAGuardar)).thenReturn(true);

        String vistaRedirigida = controladorUsuario.agregarContacto(16, requestMock);

        assertThat(vistaRedirigida, equalTo("redirect:/contactos"));
        verify(servicioUsuarioMock).agregarUsuarioAContactos(usuarioQueGuarda, usuarioAGuardar);
    }
}
