package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hamcrest.Matchers;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class RepoProyeInversionTests {

    @Autowired
    private SessionFactory sessionFactory;

    @Mock
    private Usuario usuarioMock;

    private ProyeInversionRepositorio repositorioProyeInversion;
    private RepositorioUsuarioImpl repositorioUsuarioMock;

    @BeforeEach
    public void init(){
        repositorioUsuarioMock = mock(RepositorioUsuarioImpl.class);
        this.repositorioProyeInversion = new ProyeInversionRepositorio(sessionFactory, repositorioUsuarioMock);
        this.usuarioMock = new Usuario();
        this.usuarioMock.setId(12345678);
        this.usuarioMock.setNombre("Usuario");
        this.usuarioMock.setApellido("UnoDosTresCuatro");
        this.usuarioMock.setEmail("usuario@email.com");
        this.sessionFactory.getCurrentSession().save(this.usuarioMock);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnProyecto() throws ParseException {
        ProyectoInversion proyectoInversion = dadoQueExisteUnProyecto();

        Integer proyeId = this.repositorioProyeInversion.saveProyectoInversion(proyectoInversion);

        assertThat(proyeId, Matchers.is(notNullValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaModificarElValorDeUnProyecto() {
        ProyectoInversion proyectoInversion = dadoQueExisteUnProyecto();
        Integer proyeId = this.repositorioProyeInversion.saveProyectoInversion(proyectoInversion);

        proyectoInversion.setMontoRequerido(BigDecimal.valueOf(50000));
        this.repositorioProyeInversion.updateProyeInversion(proyectoInversion);
        ProyectoInversion proyeResultado = this.repositorioProyeInversion.getProyectoById(proyeId);

        assertThat(proyeResultado.getMontoRequerido(), equalTo(BigDecimal.valueOf(50000)));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaEliminarUnProyectoExistente() throws ParseException {
        ProyectoInversion proyectoInversion = dadoQueExisteUnProyecto();
        Integer proyeId = this.repositorioProyeInversion.saveProyectoInversion(proyectoInversion);

        this.repositorioProyeInversion.deleteProyeInversion(proyeId);
        List<ProyectoInversion> proyectos = this.repositorioProyeInversion.getProyectosInversion(usuarioMock.getId());

        assertThat(proyectos.size(), equalTo(0));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanBuscarProyectosPorDescripcion() {
        String descripcion = "Autos";
        ProyectoInversion proyectoInversion = dadoQueExisteUnProyecto();
        this.repositorioProyeInversion.saveProyectoInversion(proyectoInversion);

        List<ProyectoInversion> resultado = this.repositorioProyeInversion.getMatchProyes(descripcion);

        assertThat(resultado.size(), equalTo(1));
        assertThat(resultado.get(0).getTitulo(), containsStringIgnoringCase(descripcion));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaReportarUnProyecto() throws ParseException {
        ProyectoInversion proyectoInversion = dadoQueExisteUnProyecto();
        Integer proyeId = this.repositorioProyeInversion.saveProyectoInversion(proyectoInversion);

        this.repositorioProyeInversion.reportProyeInversion(proyeId);
        ProyectoInversion proyeObtenido = this.repositorioProyeInversion.getProyectoById(proyeId);

        assertThat(proyeObtenido.getCantidadReportes(), equalTo(1));
    }

    @Test
    @Transactional
    @Rollback
    public void queSeListenTodosLosProyectosDeUnUsuario() {
        ProyectoInversion proyectoInversion = dadoQueExisteUnProyecto();
        this.repositorioProyeInversion.saveProyectoInversion(proyectoInversion);

        LocalDate fechaInicioProye = LocalDate.of(2025, 12, 6);

        ProyectoInversion proyectoInversion2 = new ProyectoInversion();
        proyectoInversion2.setTitulo("Autos nuevos");
        proyectoInversion2.setTitular(usuarioMock);
        proyectoInversion2.setDescription("Compra y venta de autos 0km.");
        proyectoInversion2.setMontoRequerido(BigDecimal.valueOf(75000));
        proyectoInversion2.setPlazoParaInicio(fechaInicioProye);
        this.repositorioProyeInversion.saveProyectoInversion(proyectoInversion2);

        List<ProyectoInversion> resultado = repositorioProyeInversion.getProyectosInversion(usuarioMock.getId());

        assertThat(resultado.size(), equalTo(2));
    }

    @Test
    @Transactional
    @Rollback
    public void queSeListenTodosLosProyectosSuspendidosDeUnUsuario() {
        ProyectoInversion proyectoInversion = dadoQueExisteUnProyecto();
        proyectoInversion.setCantidadReportes(3);
        Integer idProyecto = this.repositorioProyeInversion.saveProyectoInversion(proyectoInversion);
        this.repositorioProyeInversion.suspenderProyecto(idProyecto);

        List<ProyectoInversion> proyectosObtenidos = this.repositorioProyeInversion.getPublicacionesSuspendidas(usuarioMock.getId());

        assertThat(proyectosObtenidos.size(), equalTo(1));
    }


    private ProyectoInversion dadoQueExisteUnProyecto() {

        LocalDate fechaInicioProye = LocalDate.of(2025, 6, 6);

        ProyectoInversion proyectoInversion = new ProyectoInversion();
        proyectoInversion.setTitulo("Autos Usados");
        proyectoInversion.setTitular(usuarioMock);
        proyectoInversion.setDescription("Compra y venta de autos usados.");
        proyectoInversion.setMontoRequerido(BigDecimal.valueOf(25000));
        proyectoInversion.setPlazoParaInicio(fechaInicioProye);

        return proyectoInversion;
    }

}

