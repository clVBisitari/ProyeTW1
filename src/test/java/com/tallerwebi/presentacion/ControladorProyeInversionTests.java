package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.interfaces.ServicioProyectoInversion;
import com.tallerwebi.dominio.ServicioProyectoInversionImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
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

    private ProyectoInversion proyeInversionMock;
    private List<ProyectoInversion> proyeInvList = new ArrayList<ProyectoInversion>();
    ProyectoInversion proye1 = new ProyectoInversion();
    ProyectoInversion proye2 = new ProyectoInversion();

    @Mock
    private ServicioProyectoInversion proyeInvServiceMock;

    @InjectMocks
    private ControladorProyeInversion controlador;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        proyeInversionMock = mock(ProyectoInversion.class);
        proye1.setTitulo("Proyecto 1");
        proye1.setId(1L);
        proye2.setTitulo("Proyecto 2");
        proye2.setId(2L);
        proyeInvList.add(proye1);
        proyeInvList.add(proye2);


        proyeInvServiceMock = mock(ServicioProyectoInversionImpl.class);
        controlador = new ControladorProyeInversion(proyeInvServiceMock);
        when(proyeInvServiceMock.buscarProyectoInversion("algo")).thenReturn(proyeInvList);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void DadosDatosValidos_CuandoSellamaaBuscarProyectos_RetornaListaOk() throws Exception {

        ModelAndView modelAndView = this.controlador.buscarProyectoInversion("algo");

        assert modelAndView != null;
        var array = modelAndView.getModel().get("response");

        assertThat("redirect:inversiones", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
        assertThat(false, is(modelAndView.getModel().isEmpty()));
        assertThat(((ArrayList<?>) array).size(), is(2));
    }
}