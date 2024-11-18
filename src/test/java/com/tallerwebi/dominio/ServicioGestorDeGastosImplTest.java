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

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class ServicioGestorDeGastosImplTest {

    private ServicioGestorDeGastosImpl servicioGestorDeGastos;
    private RepositorioGestorDeGastosImpl repositorioGestorDeGastosMock;
    private RepositorioGastoImpl repositorioGastoMock;

    @BeforeEach
    public void init(){
        repositorioGestorDeGastosMock = mock(RepositorioGestorDeGastosImpl.class);
        repositorioGastoMock = mock(RepositorioGastoImpl.class);
        this.servicioGestorDeGastos = new ServicioGestorDeGastosImpl(repositorioGestorDeGastosMock, repositorioGastoMock);
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void queElSaldoComprometidoEnGastosDelMesSeGenereCorrrectamente() throws ParseException {
//        GestorDeGastos gestor = dadoQueExisteUnGestorConUnGastoDelDiaActualYDosGastosFuturos();
//        when(repositorioGestorDeGastosMock.obtenerTodosLosGastosDeUnGestor(1L)).thenReturn(gestor.getGastos());
//
//        Double totalGastosDelMes = servicioGestorDeGastos.actualizarTotalGastosDelMesEnCursoPorId(1L);
//
//        assertThat(totalGastosDelMes, equalTo(50000.0));
//    }

//    @Test
//    @Transactional
//    @Rollback
//    public void queLaCantidadDeGastosPorVencerDelMesEnCursoSeGenereCorrrectamente() throws ParseException {
//        GestorDeGastos gestor = dadoQueExisteUnGestorConUnGastoDelDiaActualYDosGastosFuturos();
//        when(repositorioGestorDeGastosMock.obtenerTodosLosGastosDeUnGestor(gestor.getId())).thenReturn(gestor.getGastos());
//
//        int cantidadGastosPorVencer = servicioGestorDeGastos.actualizarCantidadServiciosPorVencerMesEnCurso(gestor.getId());
//
//        assertThat(cantidadGastosPorVencer, equalTo(1));
//    }

    private static GestorDeGastos dadoQueExisteUnGestorConUnGastoDelDiaActualYDosGastosFuturos() throws ParseException {

        DateTimeFormatter formatoFechas = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Gasto primerGasto = new Gasto("impuesto", 50000, LocalDate.now(), Frecuencia.MENSUAL);

        String fechaSegundoGastoString = "2099-10-10";
        LocalDate fechaSegundoGastoVencimiento = LocalDate.parse(fechaSegundoGastoString);
        Gasto segundoGasto = new Gasto("impuesto", 60000, fechaSegundoGastoVencimiento, Frecuencia.MENSUAL);

        String fechaTercerGastoString = "2099-10-10";
        LocalDate fechaTercerGastoVencimiento = LocalDate.parse(fechaTercerGastoString);
        Gasto tercerGasto = new Gasto("impuesto", 70000, fechaTercerGastoVencimiento, Frecuencia.MENSUAL);

//        GestorDeGastos gestor = new GestorDeGastos();
//        //gestor.registrarGasto(primerGasto);
//        //gestor.registrarGasto(segundoGasto);
//        //gestor.registrarGasto(tercerGasto);
//
        return gestor;
        return new GestorDeGastos();

    }

//    @Test
//    @Transactional
//    @Rollback
//    public void busacarGestorDeGastosPorUsuario() {
//
//        GestorDeGastos gestor = servicioGestorDeGastos.buscarPorUsuario(1);
//
//        assertThat(gestor.getId(), equalTo(1));
//    }
}
