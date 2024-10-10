package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.ControladorUsuario;
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
    private Usuario usuarioMock;
    private ControladorUsuario controladorUsuario;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private RedirectAttributes redirectAttributesMock;

    @BeforeEach
    public void init() {
        redirectAttributesMock = mock(RedirectAttributes.class);
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("test@unlam.edu.ar");

        servicioUsuarioMock = mock(ServicioUsuario.class);
        controladorUsuario = new ControladorUsuario(servicioUsuarioMock);

        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
    }

    @Test
    public void dadoQueExisteUnUsuarioAlIrAcontactosYTenerPuedeVerLaListaDeUsuariosContacto() {

        when(requestMock.getSession(false)).thenReturn(sessionMock); // Configura el requestMock
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

        List<Usuario> contactos = new ArrayList<>();
        contactos.add(new Usuario());
        contactos.add(new Usuario());
        contactos.add(new Usuario());

        when(servicioUsuarioMock.getContactos(usuarioMock.getEmail())).thenReturn(contactos);

        ModelAndView modelAndView = controladorUsuario.irAContactos(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("contactos"));
        assertThat(modelAndView.getModel().get("contactos"), is(equalTo(contactos)));
        assertThat(modelAndView.getModel().get("contactos"), is(notNullValue()));

    }

    @Test
    public void dadoQueExisteUnUsuarioAlIrADashBoardEntoncesSeRenderizaLaVistaCorrectamenteConLosDatos() {
        when(requestMock.getSession(false)).thenReturn(sessionMock); // Configura el requestMock
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

        ModelAndView mav = controladorUsuario.irADashboard(requestMock);

        assertThat(mav.getViewName(), equalToIgnoringCase("dashboard"));
        assertThat(mav.getModel().get("usuario"), equalTo(usuarioMock));
    }

    @Test
    public void dadoQueExisteUnUsuarioYEsAdminSePuedeSuspenderAOtro(){
         // Crear un nuevo objeto Usuario para suspender
        String nombre = "marco";
        Usuario usuarioASuspender = new Usuario();
        usuarioASuspender.setId(16); // Establecer un ID ficticio
        usuarioASuspender.setNombre(nombre);
        usuarioASuspender.setEnSuspencion(true); // Simula que el usuario está suspendido

        String motivo = "mal comportamiento";

        // Configurar el comportamiento del mock de ServicioUsuario
        when(servicioUsuarioMock.buscarUsuarioPorNombre(nombre)).thenReturn(usuarioASuspender);
        doNothing().when(servicioUsuarioMock).suspenderUsuario(motivo, usuarioASuspender.getId());

        // Invocar el método bajo prueba
        String resultado = controladorUsuario.suspenderUsuario(nombre, motivo, redirectAttributesMock);

        // Verificar que se haya llamado al método buscarUsuarioPorNombre
        verify(servicioUsuarioMock).buscarUsuarioPorNombre(nombre);
        // Verificar que se haya llamado al método suspenderUsuario
        verify(servicioUsuarioMock).suspenderUsuario(motivo, usuarioASuspender.getId());
        // Verificar que se haya agregado el mensaje correcto a RedirectAttributes
        verify(redirectAttributesMock).addFlashAttribute("mensaje", "Usuario suspendido");

        // Aserción de redirección
        assertThat(resultado, is("redirect:/contactos"));

    }

    @Test
    public void dadoQueExisteUnUsuarioYEsAdminSePuedeRevertirLaSesionDeOtroUsuario(){
        // Crear un nuevo objeto Usuario para suspender
        String nombre = "marco";
        Usuario usuarioASuspender = new Usuario();
        usuarioASuspender.setId(16); // Establecer un ID ficticio
        usuarioASuspender.setNombre(nombre);
        usuarioASuspender.setEnSuspencion(true); // Simula que el usuario está suspendido

        String motivo = "mal comportamiento";

        // Configurar el comportamiento del mock de ServicioUsuario
        when(servicioUsuarioMock.buscarUsuarioPorNombre(nombre)).thenReturn(usuarioASuspender);
        doNothing().when(servicioUsuarioMock).revertirSuspensionUsuario(usuarioASuspender.getId());

        // Invocar el método bajo prueba
        String resultado = controladorUsuario.revertirSuspencion(nombre);

        // Verificar que se haya llamado al método buscarUsuarioPorNombre
        verify(servicioUsuarioMock).buscarUsuarioPorNombre(nombre);
        // Verificar que se haya llamado al método suspenderUsuario
        verify(servicioUsuarioMock).revertirSuspensionUsuario(usuarioASuspender.getId());

        // Aserción de redirección
        assertThat(resultado, is("redirect:/contactos"));

    }

/*
    @Test void siElUsuarioEstaSuspendidoLaSuspencionDeUsuarioFalla(){
        Usuario usuarioASuspender = mock(Usuario.class);
        when(usuarioASuspender.getEnSuspencion()).thenReturn(true);
        ModelAndView mav = whenSuspenderUsuario(usuarioASuspender);

        //then --cuando el metodo no es exitoso es porque me mando a la vista de contactos (usuario al que decoues se le pone una advertencia)

        thenLaSuspencionEsFallida(mav);

    }

    private String whenSuspenderUsuario(String motivo, String nombre, RedirectAttributes redirectAttributesMock) {
        //    verify(servicioUsuarioMock).buscarUsuarioPorNombre(nombre);

        String mensajeExitoso =  controladorUsuario.suspenderUsuario(motivo,nombre, redirectAttributesMock);

        return mensajeExitoso;
    }

    private void thenLaSuspencionEsExitosa(String mensajeExitoso) {

        assertThat(mensajeExitoso, is("redirect:/contactos"));

    }
    private void thenLaSuspencionEsFallida(ModelAndView mav) {

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/contactos"));
    }*/
}