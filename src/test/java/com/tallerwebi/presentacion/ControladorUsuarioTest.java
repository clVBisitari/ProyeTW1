/*package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.interfaces.ServicioGestorGastos;
import com.tallerwebi.dominio.interfaces.ServicioProyectoInversion;
import com.tallerwebi.dominio.interfaces.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

public class ControladorUsuarioTest {

    @Mock
    private ServicioUsuario servicioUsuarioMock = mock(ServicioUsuario.class);
    @Mock
    private ServicioGestorGastos servicioGestorGastosMock = mock(ServicioGestorGastos.class);
    @Mock
    private ServicioProyectoInversion servicioProyectionInversionMock  = mock(ServicioProyectoInversion.class);
    @Mock
    private UsuarioDTO usuarioDTOMock = mock(UsuarioDTO.class);
    @Mock
    private Usuario usuarioMock = mock(Usuario.class);
    @Mock
    private HttpServletRequest requestMock = mock(HttpServletRequest.class);
    @Mock
    private HttpSession sessionMock = mock(HttpSession.class);
    @Mock
    private RedirectAttributes redirectAttributesMock = mock(RedirectAttributes.class);

    private ControladorUsuario controladorUsuario;

    @BeforeEach
    public void init() {

        when(this.usuarioMock.getId()).thenReturn(2);
        when(this.usuarioMock.getDni()).thenReturn(12345678);
        when(this.usuarioMock.getEmail()).thenReturn("email@email.com");
        when(this.usuarioMock.getNombre()).thenReturn("Nombre");
        when(this.usuarioMock.getApellido()).thenReturn("Apellido");
        when(this.usuarioMock.getEnSuspension()).thenReturn(false);
        when(this.usuarioMock.getEsAdmin()).thenReturn(false);
        when(this.usuarioMock.getSaldo()).thenReturn(new BigDecimal(123));

        when(requestMock.getSession(false)).thenReturn(sessionMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioDTOMock);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(2);
//        when(requestMock.getSession(true)).thenReturn(sessionMock);
        controladorUsuario = new ControladorUsuario(servicioUsuarioMock, servicioGestorGastosMock, servicioProyectionInversionMock);
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
//        when(sessionMock.getAttribute("idUsuario")).thenReturn(2);
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
        when(this.servicioUsuarioMock.getUsuarioById(2)).thenReturn(this.usuarioMock);
        ModelAndView mav = controladorUsuario.irADashboard(requestMock);
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(2);
        usuarioDTO.setDni(12345678);
        usuarioDTO.setNombre("Nombre");
        usuarioDTO.setApellido("Apellido");
        usuarioDTO.setEmail("email@email.com");
        usuarioDTO.setEnSuspension(false);
        usuarioDTO.setEsAdmin(false);
        usuarioDTO.setSaldo(new BigDecimal(123));

        assertThat(mav.getViewName(), equalToIgnoringCase("dashboard"));
        assertThat(mav.getModel().get("usuario"), equalTo(usuarioDTO));
    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoPuedeSuspenderAOtro() {
        Usuario usuarioASuspender = new Usuario();
        usuarioASuspender.setId(16);
        usuarioASuspender.setNombre("Alexi");
        usuarioASuspender.setEnSuspension(false);
        String motivo = "mal comportamiento";

        when(servicioUsuarioMock.getUsuarioById(16)).thenReturn(usuarioASuspender);
        when(servicioUsuarioMock.suspenderUsuario(eq("mal comportamiento"), eq(16))).thenReturn(true);

        String resultado = controladorUsuario.suspenderUsuario(usuarioASuspender.getId(), motivo, redirectAttributesMock);
        assertThat(resultado, is("redirect:/contactos"));
    }


    @Test
    public void dadoQueExisteUnUsuarioLogueadoYEsAdministradorPuedeRevertirUnaSuspension() {
        Usuario usuarioASuspender = new Usuario();
        usuarioASuspender.setId(16);
        when(servicioUsuarioMock.getUsuarioById(16)).thenReturn(usuarioASuspender);
        when(servicioUsuarioMock.revertirSuspensionUsuario(usuarioASuspender.getId())).thenReturn(true);

        String resultado = controladorUsuario.revertirSuspencion(usuarioASuspender.getId(), redirectAttributesMock);

        verify(servicioUsuarioMock).revertirSuspensionUsuario(usuarioASuspender.getId());

        verify(redirectAttributesMock).addFlashAttribute("mensaje", "Suspensión revertida");

        assertThat(resultado, is("redirect:/vistaAdministrador"));
    }
    @Test
    public void dadoQueExisteUnUsuarioLogueadoYEsAdministradorPuedeDesactivarAUsuariosSuspendidos() throws Exception {
        when(requestMock.getSession(false)).thenReturn(sessionMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioDTOMock);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(1);
        when(usuarioDTOMock.getEsAdmin()).thenReturn(true);
        when(usuarioDTOMock.getId()).thenReturn(1);

        Usuario usuarioADesactivar = new Usuario();
        usuarioADesactivar.setId(2);

        when(Usuario.isUserLoggedIn(requestMock)).thenReturn(true);
        when(Usuario.isAdmin(requestMock)).thenReturn(true);
        when(servicioUsuarioMock.getUsuarioById(2)).thenReturn(usuarioADesactivar);

        String resultado = controladorUsuario.cambiarEstadoUsuario(2, requestMock);
        verify(servicioUsuarioMock).cambiarEstadoUsuario(usuarioADesactivar);
        assertThat(resultado, is("redirect:/vistaAdministrador"));

    }
    @Test
    public void dadoQueExisteUnUsuarioLogueadoYEsAdministradorPuedeReactivarAUsuariosSuspendidos() throws Exception {
        when(requestMock.getSession(false)).thenReturn(sessionMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioDTOMock);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(1);
        when(usuarioDTOMock.getEsAdmin()).thenReturn(true);
        when(usuarioDTOMock.getId()).thenReturn(1);

        Usuario usuarioAActivar = new Usuario();
        usuarioAActivar.setId(2);
        usuarioAActivar.setEstaActivo(false);

        when(Usuario.isUserLoggedIn(requestMock)).thenReturn(true);
        when(Usuario.isAdmin(requestMock)).thenReturn(true);
        when(servicioUsuarioMock.getUsuarioById(2)).thenReturn(usuarioAActivar);

        String resultado = controladorUsuario.cambiarEstadoUsuario(2, requestMock);
        verify(servicioUsuarioMock).cambiarEstadoUsuario(usuarioAActivar);
        assertThat(resultado, is("redirect:/vistaAdministrador"));

    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoYEsAdministradorPuedeAccederAlaVistaAdministrador() {

        // Mock de la sesión y los atributos de la sesión
        when(requestMock.getSession(false)).thenReturn(sessionMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioDTOMock);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(1);
        when(usuarioDTOMock.getEsAdmin()).thenReturn(true);
        when(usuarioDTOMock.getId()).thenReturn(1);

        // Simulación de que el usuario está logueado y es admin
        when(Usuario.isUserLoggedIn(requestMock)).thenReturn(true);
        when(Usuario.isAdmin(requestMock)).thenReturn(true);

        // Usuarios a ser guardados
        List<Usuario> usuariosSuspendidos = new ArrayList<>();
        usuariosSuspendidos.add(new Usuario());
        usuariosSuspendidos.add(new Usuario());
        usuariosSuspendidos.add(new Usuario());

        // Mock de servicios
        when(servicioUsuarioMock.getUsuariosSuspedidos()).thenReturn(usuariosSuspendidos);
        List<UsuarioDTO> usuariosSuspendidosDTO=  Usuario.mapToUsuarioDTOList(usuariosSuspendidos);
        ModelAndView mav = controladorUsuario.irAAdmin(requestMock);

        assertThat(mav.getViewName(), equalToIgnoringCase("vistaAdministrador"));
        assertThat(mav.getModel().get("usuariosSuspendidos"), equalTo(usuariosSuspendidosDTO));
    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoPuedeAgregarOtroUsuarioQueNoEsteEnSuListaDeContactos() throws Exception {

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
*/