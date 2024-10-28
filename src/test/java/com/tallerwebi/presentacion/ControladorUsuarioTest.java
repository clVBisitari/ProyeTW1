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

        ModelAndView mav = controladorUsuario.irADashboard(requestMock);

        assertThat(mav.getViewName(), equalToIgnoringCase("dashboard"));
        assertThat(mav.getModel().get("usuario"), equalTo(usuarioDTOMock));
    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoYEsAdminSePuedeSuspenderAOtro() {

        String nombre = "marco";
        Usuario usuarioASuspender = new Usuario();
        usuarioASuspender.setId(16);
        usuarioASuspender.setNombre(nombre);
        usuarioASuspender.setEnSuspension(true);

        String motivo = "mal comportamiento";

        when(servicioUsuarioMock.getUsuarioById(16)).thenReturn(usuarioASuspender);
        doNothing().when(servicioUsuarioMock).suspenderUsuario(motivo, usuarioASuspender.getId());

        String resultado = controladorUsuario.suspenderUsuario(usuarioASuspender.getId(), motivo, redirectAttributesMock);

        verify(servicioUsuarioMock).buscarUsuarioPorNombre(nombre);
        verify(servicioUsuarioMock).suspenderUsuario(motivo, usuarioASuspender.getId());
        verify(redirectAttributesMock).addFlashAttribute("mensaje", "Usuario suspendido");


        assertThat(resultado, is("redirect:/contactos"));

    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoYEsAdminSePuedeRevertirLaSuspensionDeOtroUsuario() {
        String nombre = "marco";
        Usuario usuarioASuspender = new Usuario();
        usuarioASuspender.setId(16);
        usuarioASuspender.setNombre(nombre);
        usuarioASuspender.setEnSuspension(true);

        when(servicioUsuarioMock.getUsuarioById(16)).thenReturn(usuarioASuspender);
        doNothing().when(servicioUsuarioMock).revertirSuspensionUsuario(usuarioASuspender.getId());

        String resultado = controladorUsuario.revertirSuspencion(usuarioASuspender.getId());

        verify(servicioUsuarioMock).buscarUsuarioPorNombre(nombre);
        verify(servicioUsuarioMock).revertirSuspensionUsuario(usuarioASuspender.getId());

        assertThat(resultado, is("redirect:/contactos"));

    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoPuedeAgregarOtroUsuarioQueNoEsteEnSuListaDeContactos() {
        when(requestMock.getSession(false)).thenReturn(sessionMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioDTOMock);
        Usuario usuarioQueGuarda = new Usuario();
        usuarioQueGuarda.setEmail("userMock@hotmail.com");
        Usuario usuarioAGuardar = new Usuario();

        when(servicioUsuarioMock.getUsuarioById(usuarioDTOMock.getId())).thenReturn(usuarioQueGuarda);
        when(servicioUsuarioMock.getUsuarioById(16)).thenReturn(usuarioAGuardar);

        String vistaRedirigida = controladorUsuario.agregarContacto(16, requestMock);

        verify(servicioUsuarioMock).buscarUsuarioPorNombre(usuarioDTOMock.getNombre());
        verify(servicioUsuarioMock).buscarUsuarioPorNombre("nombre");
        verify(servicioUsuarioMock).agregarUsuarioAContactos(usuarioQueGuarda, usuarioAGuardar);

        assertThat(vistaRedirigida, equalToIgnoringCase("redirect:/contactos"));

    }
    }
