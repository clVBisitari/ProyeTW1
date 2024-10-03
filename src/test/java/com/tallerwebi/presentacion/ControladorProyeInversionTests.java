package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.ServicioProyectoInversion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorProyeInversionTests {

    private ProyectoInversion proyeInversionMock;
    private List<ProyectoInversion> proyeInvList;
    ProyectoInversion proye1 = new ProyectoInversion();
    ProyectoInversion proye2 = new ProyectoInversion();

    @InjectMocks
    private ServicioProyectoInversion proyeInvServiceMock;

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
        proyeInvList = Arrays.asList(proye1, proye2);

        when(proyeInversionMock.getTitulo()).thenReturn("ProyeInversion Mock");

        proyeInvServiceMock = mock(ServicioProyectoInversion.class);

        when(proyeInvServiceMock.buscarProyectoInversion("algo")).thenReturn(proyeInvList);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void DadosDatosValidos_CuandoSellamaaBuscarProyectos_RetornaListaOk() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/buscarProyeInversion").param("nombre","algo"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("response"))
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("redirect:inversiones", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
        assertThat(false, is(modelAndView.getModel().isEmpty()));
    }

}
