package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.ControladorUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

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
    private UsuarioDTO usuarioMock;

    private ControladorUsuario controladorUsuario;

    @BeforeEach

    public void init(){
        servicioUsuarioMock = mock(ServicioUsuario.class);
        controladorUsuario  = new ControladorUsuario(servicioUsuarioMock);
        usuarioMock = mock(UsuarioDTO.class);
        when(usuarioMock.getEmail()).thenReturn("test@unlam.edu.ar");
    }
@Test
    public void dadoQueExisteUnUsuarioAlIrAcontactosPuedeVerLaListaDeUsuariosContacto(){
       UsuarioDTO usuarioMock =mock(UsuarioDTO.class);
       ArrayList<UsuarioDTO> contactos = new ArrayList<>();
       contactos.add(new UsuarioDTO());
       contactos.add(new UsuarioDTO());
       contactos.add(new UsuarioDTO());

    when(usuarioMock.getContactos()).thenReturn(contactos);

       ModelAndView mav = whenListarContactos(usuarioMock);

       thenListarUsuariosEsExitoso(mav);
    }

    private ModelAndView whenListarContactos(UsuarioDTO usuarioMock) {
        ModelAndView mav =  controladorUsuario.irAContactos(usuarioMock);
        return mav;/// sabemos que va a devolver un model and view
    }
    private void thenListarUsuariosEsExitoso(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("contactos"));
        assertThat(mav.getModel().get("contactos"), is(notNullValue()));

    }



/*
    @Test
    public void dadoQueExisteUnUsuarioYEsAdminSePuedeSuspenderAOtro(){

        //given
        //when
        Usuario usuarioQueSuspende = mock(Usuario.class);
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