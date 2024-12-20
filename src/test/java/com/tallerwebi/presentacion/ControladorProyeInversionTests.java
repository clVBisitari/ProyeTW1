package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.interfaces.ServicioMercadoPago;
import com.tallerwebi.dominio.interfaces.ServicioProyectoInversion;
import com.tallerwebi.dominio.interfaces.ServicioUsuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorProyeInversionTests {

    private final List<ProyectoInversion> proyeInvList = new ArrayList<>();
    ProyectoInversion proye1 = new ProyectoInversion();
    ProyectoInversion proye2 = new ProyectoInversion();

    @Mock
    private ServicioProyectoInversion proyeInvServiceMock;
    private ServicioMercadoPago mpServiceMock;
    private ServicioUsuario usuarioServiceMock;
    private HttpServletRequest requestMock = mock(HttpServletRequest.class);
    private Usuario userMock;
    private HttpSession sessionMock = mock(HttpSession.class);
    @InjectMocks
    private ControladorProyeInversion controlador;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Mock
    private RedirectAttributes redirectAttributesMock = mock(RedirectAttributes.class);


    @BeforeEach
    public void init(){
        when(requestMock.getSession(false)).thenReturn(this.sessionMock);
        proye1.setTitulo("Proyecto 1");
        proye1.setId(1);
        proye2.setTitulo("Proyecto 2");
        proye2.setId(2);
        proyeInvList.add(proye1);
        proyeInvList.add(proye2);
        requestMock = mock(HttpServletRequest.class);
        userMock = new Usuario();
        userMock.setId(1234);
        userMock.setEmail("a@a.com");
        userMock.setNombre("Usuario 1");
        userMock.setApellido("Apellido 1");
        userMock.setPassword("123456");

        proyeInvServiceMock = mock(ServicioProyectoInversionImpl.class);
        mpServiceMock = mock(ServicioMercadoPagoImpl.class);
        usuarioServiceMock = mock(ServicioUsuarioImpl.class);
        controlador = new ControladorProyeInversion(proyeInvServiceMock, mpServiceMock, usuarioServiceMock);
        when(proyeInvServiceMock.buscarProyectoInversion("algo")).thenReturn(proyeInvList);
        when(requestMock.getSession()).thenReturn(mock(HttpSession.class));

//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    @Test
    public void DadosDatosValidos_CuandoSellamaaBuscarProyectos_RetornaListaOk() throws Exception {

        String string = this.controlador.buscarProyectoInversion("algo",redirectAttributesMock, requestMock);
        assertThat("redirect:inversiones", equalToIgnoringCase(string));
    }

  /*  @Test
    public void DadosDatosValidos_CuandoSellamaaBuscarProyectos_RetornaListaOk() throws Exception {

     String string = this.controlador.buscarProyectoInversion("algo");

        assert modelAndView != null;
        var array = modelAndView.getModel().get("response");

        assertThat("redirect:inversiones", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
        assertThat(true,     is(modelAndView.getModel().containsValue(array)));
        assertThat(((ArrayList<?>) array).size(), is(2));
    }*/

    @Test
    public void DadosDatosValidos_CuandoGetProyectosSugeridos_RetornaListaDeProyectos(){

        when(this.proyeInvServiceMock.getProyectosMayorInversion()).thenReturn(proyeInvList);
        when(requestMock.getSession(false)).thenReturn(this.sessionMock);
        when(this.sessionMock.getAttribute("idUsuario")).thenReturn(1234);
        when(this.sessionMock.getAttribute("USUARIO")).thenReturn(this.userMock);

        ModelAndView modelAndView = this.controlador.getAll(this.requestMock);

        assert modelAndView != null;
        var array = modelAndView.getModel().get("proyesRecomendados");

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("inversiones"));
        assertThat(false, is(modelAndView.getModel().isEmpty()));
        assertThat(((ArrayList<ProyectoInversion>) array).size(), is(2));
    }
}