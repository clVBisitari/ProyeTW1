package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Frecuencia;
import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.DateFormatter;
import javax.transaction.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class RepositorioGastoImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioGastoImpl repositorioGastoImpl;
    private DateFormat formatoFechas = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void init(){
        this.repositorioGastoImpl = new RepositorioGastoImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnGasto() throws ParseException {
        dadoQueExisteUnGasto();

        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();

        assertThat(gastos.size(), equalTo(1));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaModificarElValorDeUnGastoExistente() throws ParseException {
        dadoQueExisteUnGasto();

        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();
        Gasto gastoAModificar = gastos.get(0);
        gastoAModificar.setValor(7000);
        this.repositorioGastoImpl.modificarValorDeUnGasto(gastoAModificar.getId(), gastoAModificar.getValor());
        Gasto gastoModificado = this.repositorioGastoImpl.buscarGastoPorId(gastoAModificar.getId());
        double valorEsperado = gastoModificado.getValor();

        assertThat(valorEsperado, equalTo((double)7000));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaEliminarUnGastoExistente() throws ParseException {
        dadoQueExisteUnGasto();

        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();
        Gasto gastoAEliminar = gastos.get(0);
        this.repositorioGastoImpl.eliminarGasto(gastoAEliminar.getId());
        gastos.removeIf(Gasto -> Gasto.getId() == gastoAEliminar.getId());
        gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();

        assertThat(gastos.size(), equalTo(0));
    }

    @Test
    @Transactional
    @Rollback
    public void QueSePuedaBuscarUnGastoPorDescripcion() throws ParseException {
        dadoQueExisteUnGasto();

        List<Gasto> gastos = this.repositorioGastoImpl.buscarGastoPorDescripcion("impuesto");

        assertThat(gastos.size(), equalTo(1));
    }

    @Test
    @Transactional
    @Rollback
    public void QueSePuedaModificarLaFechaDeVencimientoDeUnGasto() throws ParseException {
        dadoQueExisteUnGasto();

        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();
        Gasto gastoAModificar = gastos.get(0);
        String fechaString = "2024-12-10";
        Date nuevaFechaVencimiento = formatoFechas.parse(fechaString);
        gastoAModificar.setFechaVencimiento(nuevaFechaVencimiento);
        this.repositorioGastoImpl.modificarFechaDeVencimientoDeUnGasto(gastoAModificar.getId(), gastoAModificar.getFechaVencimiento());
        Gasto gastoModificado = this.repositorioGastoImpl.buscarGastoPorId(gastoAModificar.getId());
        Date fechaObtenida = gastoModificado.getFechaVencimiento();

        assertThat(fechaObtenida, equalTo(nuevaFechaVencimiento));
    }

    @Test
    @Transactional
    @Rollback
    public void QueSePuedaModificarLaFechaDeRecordatorioDeUnGasto() throws ParseException {
        dadoQueExisteUnGasto();

        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();
        Gasto gastoAModificar = gastos.get(0);
        String fechaString = "2024-12-08";
        Date nuevaFechaVencimiento = formatoFechas.parse(fechaString);
        gastoAModificar.setFechaRecordatorio(nuevaFechaVencimiento);
        this.repositorioGastoImpl.modificarFechaDeRecordatorioDeUnGasto(gastoAModificar.getId(), gastoAModificar.getFechaRecordatorio());
        Gasto gastoModificado = this.repositorioGastoImpl.buscarGastoPorId(gastoAModificar.getId());
        Date fechaObtenida = gastoModificado.getFechaRecordatorio();

        assertThat(fechaObtenida, equalTo(nuevaFechaVencimiento));
    }

    @Test
    @Transactional
    @Rollback
    public void QueSePuedaModificarLaFrecuenciaDeUnGasto() throws ParseException {
        dadoQueExisteUnGasto();

        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();
        Gasto gastoAModificar = gastos.get(0);
        gastoAModificar.setFrecuencia(Frecuencia.SEMESTRAL);
        this.repositorioGastoImpl.modificarFrecuenciaDeUnGasto(gastoAModificar.getId(), gastoAModificar.getFrecuencia());
        Gasto gastoModificado = this.repositorioGastoImpl.buscarGastoPorId(gastoAModificar.getId());
        Frecuencia frecuenciaEsperada = gastoModificado.getFrecuencia();

        assertThat(frecuenciaEsperada, equalTo(Frecuencia.SEMESTRAL));
    }


    private void dadoQueExisteUnGasto() throws ParseException {
        String fechaString = "2024-10-10";
        Date fechaVencimiento = formatoFechas.parse(fechaString);
        Gasto gasto = new Gasto("impuesto", 50000, fechaVencimiento, Frecuencia.MENSUAL);
        this.sessionFactory.getCurrentSession().save(gasto);
    }

}

