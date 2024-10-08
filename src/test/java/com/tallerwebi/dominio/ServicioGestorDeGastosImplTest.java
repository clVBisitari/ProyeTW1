package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioGestorDeGastosImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class ServicioGestorDeGastosImplTest {

    private ServicioGestorDeGastosImpl servicioGestorDeGastos;
    private RepositorioGestorDeGastosImpl repositorioGestorDeGastosMock;

    @BeforeEach
    public void init(){
        repositorioGestorDeGastosMock = mock(RepositorioGestorDeGastosImpl.class);
        this.servicioGestorDeGastos = new ServicioGestorDeGastosImpl(repositorioGestorDeGastosMock);
    }

    @Test
    @Transactional
    @Rollback
    public void queElSaldoComprometidoEnGastosDelMesSeGenereCorrrectamente() throws ParseException {
        DateFormat formatoFechas = new SimpleDateFormat("yyyy-MM-dd");

        Gasto primerGasto = new Gasto("impuesto", 50000, new Date(), Frecuencia.MENSUAL);

        String fechaSegundoGastoString = "2099-10-10";
        Date fechaSegundoGastoVencimiento = formatoFechas.parse(fechaSegundoGastoString);
        Gasto segundoGasto = new Gasto("impuesto", 60000, fechaSegundoGastoVencimiento, Frecuencia.MENSUAL);

        String fechaTercerGastoString = "2099-10-10";
        Date fechaTercerGastoVencimiento = formatoFechas.parse(fechaTercerGastoString);
        Gasto tercerGasto = new Gasto("impuesto", 70000, fechaTercerGastoVencimiento, Frecuencia.MENSUAL);

        GestorDeGastos gestor = new GestorDeGastos();
        gestor.registrarGasto(primerGasto);
        gestor.registrarGasto(segundoGasto);
        gestor.registrarGasto(tercerGasto);
        when(repositorioGestorDeGastosMock.obtenerTodosLosGastosDeUnGestor(1L)).thenReturn(gestor.getGastos());

        Double totalGastosDelMes = servicioGestorDeGastos.actualizarTotalGastosDelMesEnCursoPorId(1L);

        assertThat(totalGastosDelMes, equalTo(50000.0));
    }
}
