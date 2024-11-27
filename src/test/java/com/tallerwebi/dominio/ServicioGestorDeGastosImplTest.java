package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioGastoImpl;
import com.tallerwebi.infraestructura.RepositorioGestorDeGastosImpl;
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
public class ServicioGestorDeGastosImplTest {

    private Usuario usuarioMock;
    private RepositorioGestorDeGastosImpl repositorioGestorDeGastosMock;
    private RepositorioGastoImpl repositorioGastoMock;
    private ServicioGestorDeGastosImpl servicioGestorDeGastos;

    @BeforeEach
    public void init(){
        usuarioMock = mock(Usuario.class);
        repositorioGastoMock = mock(RepositorioGastoImpl.class);
        repositorioGestorDeGastosMock = mock(RepositorioGestorDeGastosImpl.class);
        this.servicioGestorDeGastos = new ServicioGestorDeGastosImpl(repositorioGestorDeGastosMock, repositorioGastoMock);
    }

    @Test
    @Transactional
    @Rollback
    public void queElSaldoComprometidoEnGastosDelMesSeGenereCorrrectamente() throws ParseException {
        List<Gasto> gastos = dadoQueExisteUnUsuarioConUnGastoDelDiaActualYDosGastosFuturos();
        when(repositorioGastoMock.obtenerTodosLosGastosPorUsuarioId(1)).thenReturn(gastos);

        BigDecimal totalGastosDelMes = servicioGestorDeGastos.actualizarTotalGastosDelMesEnCursoPorId(1);

        assertThat(totalGastosDelMes, equalTo(BigDecimal.valueOf(50000)));
    }

    @Test
    @Transactional
    @Rollback
    public void queLaCantidadDeGastosPorVencerDelMesEnCursoSeGenereCorrrectamente() throws ParseException {
        List<Gasto> gastos = dadoQueExisteUnUsuarioConUnGastoDelDiaActualYDosGastosFuturos();
        when(repositorioGastoMock.obtenerTodosLosGastosPorUsuarioId(1)).thenReturn(gastos);

        int cantidadGastosPorVencer = servicioGestorDeGastos.actualizarCantidadServiciosPorVencerMesEnCurso(1);

        assertThat(cantidadGastosPorVencer, equalTo(1));
    }

    private List<Gasto> dadoQueExisteUnUsuarioConUnGastoDelDiaActualYDosGastosFuturos() throws ParseException {

        LocalDate fechaActual = LocalDate.now();
        Date fechaDeHoy = Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Gasto primerGasto = new Gasto("impuesto", new BigDecimal(50000), fechaDeHoy, Frecuencia.MENSUAL);

        String fechaSegundoGastoString = "2099-10-10";
        Date fechaSegundoGastoVencimiento = Date.from(LocalDate.parse(fechaSegundoGastoString).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Gasto segundoGasto = new Gasto("Impuesto", new BigDecimal(60000), fechaSegundoGastoVencimiento, Frecuencia.MENSUAL);

        String fechaTercerGastoString = "2099-10-10";
        Date fechaTercerGastoVencimiento = Date.from(LocalDate.parse(fechaTercerGastoString).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Gasto tercerGasto = new Gasto("impuesto", new BigDecimal(70000), fechaTercerGastoVencimiento, Frecuencia.MENSUAL);

        List<Gasto> gastos = new ArrayList<>();
        gastos.add(primerGasto);
        gastos.add(segundoGasto);
        gastos.add(tercerGasto);
        return gastos;
    }
}
