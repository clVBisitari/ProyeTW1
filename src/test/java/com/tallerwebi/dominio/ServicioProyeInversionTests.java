package com.tallerwebi.dominio;

import com.tallerwebi.dominio.interfaces.ServicioProyectoInversion;
import com.tallerwebi.infraestructura.ProyeInversionRepositorio;
import com.tallerwebi.infraestructura.RepositorioGastoImpl;
import com.tallerwebi.infraestructura.RepositorioGestorDeGastosImpl;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class ServicioProyeInversionTests {

    private Usuario usuarioMock;
    private ProyeInversionRepositorio repositorioProyeInversionMock;
    private ServicioProyectoInversionImpl servicioProyectoInversion;
    private RepositorioUsuarioImpl repositorioUsuarioMock;

    @BeforeEach
    public void init(){
        usuarioMock = mock(Usuario.class);
        repositorioProyeInversionMock = mock(ProyeInversionRepositorio.class);
        repositorioUsuarioMock = mock(RepositorioUsuarioImpl.class);
        this.servicioProyectoInversion = new ServicioProyectoInversionImpl(repositorioProyeInversionMock, repositorioUsuarioMock);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaSuspenderUnProyecto() {
        ProyectoInversion proyectoInversion = dadoQueExisteUnProyecto();
        proyectoInversion.setCantidadReportes(3);
        Integer idProyecto = this.repositorioProyeInversionMock.saveProyectoInversion(proyectoInversion);
        when(repositorioProyeInversionMock.getProyectoById(idProyecto)).thenReturn(proyectoInversion);
        when(repositorioProyeInversionMock.suspenderProyecto(idProyecto)).thenReturn(true);

        boolean suspensionExitosa = this.servicioProyectoInversion.suspenderProyecto(idProyecto);

        assertThat(suspensionExitosa, equalTo(true));
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
