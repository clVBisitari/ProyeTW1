package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Frecuencia;
import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.dominio.GestorDeGastos;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hibernate.SessionFactory;
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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class RepositorioGestorDeGastosImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioGestorDeGastosImpl repositorioGestorDeGastosImpl;
    private DateFormat formatoFechas = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void init(){
        this.repositorioGestorDeGastosImpl = new RepositorioGestorDeGastosImpl(sessionFactory);
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaCrearUnGestorDeGastos(){

        GestorDeGastos gestor  = new GestorDeGastos();

        this.sessionFactory.getCurrentSession().save(gestor);

        List<GestorDeGastos> gestorCreado = this.repositorioGestorDeGastosImpl.obtenerTodosLosGestores();

        assertThat(gestorCreado.size(), equalTo(1));
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaRegistrarUnGastoEnElGestorDeGastos() throws ParseException {
        GestorDeGastos gestor = dadoQueExisteUnGestorConUnGastoRegistrado();

        List<Gasto> gastos = this.repositorioGestorDeGastosImpl.obtenerTodosLosGastosDeUnGestor(gestor.getId());

        assertThat(gastos.size(), equalTo(1));
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaEliminarUnGastoEnElGestorDeGastos() throws ParseException {
        GestorDeGastos gestor = dadoQueExisteUnGestorConUnGastoRegistrado();

        List<Gasto> gastos = this.repositorioGestorDeGastosImpl.obtenerTodosLosGastosDeUnGestor(gestor.getId());
        Gasto gasto = gastos.get(0);
        gestor.eliminarGasto(gasto);

        this.sessionFactory.getCurrentSession().save(gestor);
        gastos = this.repositorioGestorDeGastosImpl.obtenerTodosLosGastosDeUnGestor(gestor.getId());

        assertThat(gastos.size(), equalTo(0));
    }


    @Test
    @Rollback
    @Transactional
    public void queSePuedaRegistrarOtroGastoEnElGestorDeGastos(){

    }

    /*@Test
    @Rollback
    @Transactional
    public void queElTotalDeGastosDelMesEnCursoSeActualiceAlRegistrarNuevosGastos() throws ParseException {
        String fechaString = "2024-10-10";
        DateFormat formatoFechas = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaVencimiento = formatoFechas.parse(fechaString);
        Gasto gasto = new Gasto("impuesto", 50000, fechaVencimiento, Frecuencia.MENSUAL);
        GestorDeGastos gestor = new GestorDeGastos();
        gestor.registrarGasto(gasto);
        this.sessionFactory.getCurrentSession().save(gasto);


    }*/

    private GestorDeGastos dadoQueExisteUnGestorConUnGastoRegistrado() throws ParseException {

        String fechaString = "2024-10-10";
        DateTimeFormatter formatoFechas = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaVencimiento = LocalDate.parse(fechaString, formatoFechas);

        Gasto gasto = new Gasto("impuesto", 5000, fechaVencimiento, Frecuencia.MENSUAL);
        GestorDeGastos gestor = new GestorDeGastos();

        // Registrar el gasto en el gestor si el metodo está disponible
        //gestor.registrarGasto(gasto);

        // Guardar el gestor en la base de datos junto con el gasto registrado
        this.sessionFactory.getCurrentSession().save(gestor);

        return gestor;
    }

    /*@Test
    @Rollback
    @Transactional
    public void queSePuedaModificarUnGastoEnElGastorDeGastos(){
        Gasto gasto = new Gasto("impuesto",5000);
        ServicioGestorDeGastosImpl
    }*/

}
