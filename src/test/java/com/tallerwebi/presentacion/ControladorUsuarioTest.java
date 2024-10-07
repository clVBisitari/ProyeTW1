package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.ControladorUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorUsuarioTest {

    private ServicioUsuario servicioUsuarioMock;
    private Usuario usuarioMock;
    private ControladorUsuario controladorUsuario;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach

    public void init(){
        servicioUsuarioMock = mock(ServicioUsuario.class);
        controladorUsuario  = new ControladorUsuario(servicioUsuarioMock);
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("test@unlam.edu.ar");
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
    }
  @Test
    public void dadoQueExisteUnUsuarioAlIrAcontactosYTenerPuedeVerLaListaDeUsuariosContacto(){

      when(requestMock.getSession(false)).thenReturn(sessionMock); // Configura el requestMock
      when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
      //    when(usuarioMock.getEmail()).thenReturn("test@unlam.edu.ar");
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
    public void dadoQueExisteUnUsuarioAlIrADashBoardEntoncesSeRenderizaLaVistaCorrectamenteConLosDatos(){

      when(requestMock.getSession(false)).thenReturn(sessionMock); // Configura el requestMock
      when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

        ModelAndView mav = cuandoVaADashBoard(requestMock);
        entoncesSeRenderizaLaVistaConDatos(mav);
    }
    private void entoncesSeRenderizaLaVistaConDatos( ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("dashboard"));
        assertThat(mav.getModel().get("usuario"), equalTo(usuarioMock));
    }

    private ModelAndView cuandoVaADashBoard(HttpServletRequest request) {
      return controladorUsuario.irADashboard(request);
    }

    /*
    @Test
    public void dadoQueExisteUnUsuarioYEsAdminSePuedeSuspenderAOtro(){

        //given
        //when
        UsuarioDTO usuarioQueSuspende = mock(UsuarioDTO.class);
        when(usuarioQueSuspende.getEsAdmin()).thenReturn(true);
        Usuario usuarioASuspender= mock(Usuario.class);
        ModelAndView mav = whenSuspenderUsuario(usuarioASuspender);

        //then --cuando el metodo es exitoso es porque me mando a la vista de contactos (usuario al que decoues se le pone una advertencia)

        thenLaSuspencionEsExitosa(mav);

    }

    private ModelAndView whenSuspenderUsuario(Usuario userASuspender) {
      ModelAndView mav =  controladorUsuario.suspenderUsuario(userASuspender);
      return mav;/// sabemos que va a devolver un model and view
    }

    private void thenLaSuspencionEsExitosa(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/contactos"));
    }


    @Test void siElUsuarioEstaSuspendidoLaSuspencionDeUsuarioFalla(){
        Usuario usuarioASuspender = mock(Usuario.class);
        when(usuarioASuspender.getEnSuspencion()).thenReturn(true);
        ModelAndView mav = whenSuspenderUsuario(usuarioASuspender);

        //then --cuando el metodo no es exitoso es porque me mando a la vista de contactos (usuario al que decoues se le pone una advertencia)

        thenLaSuspencionEsFallida(mav);

    }

    private void thenLaSuspencionEsFallida(ModelAndView mav) {

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/contactos"));
    }*/
}