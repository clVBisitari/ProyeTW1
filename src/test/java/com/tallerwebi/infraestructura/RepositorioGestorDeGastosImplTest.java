package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Frecuencia;
import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.dominio.GestorDeGastos;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class RepositorioGestorDeGastosImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioGestorDeGastosImpl repositorioGestorDeGastosImpl;
    private RepositorioGastoImpl repoGastoImpl;
    private DateFormat formatoFechas = new SimpleDateFormat("yyyy-MM-dd");
    private Gasto gasto1;

    @Mock
    private Usuario usuarioMock;
    private List<Gasto>gastos;

    @BeforeEach
    public void init(){
        this.repositorioGestorDeGastosImpl = new RepositorioGestorDeGastosImpl(sessionFactory);
        this.repoGastoImpl = new RepositorioGastoImpl(sessionFactory);

        MockitoAnnotations.openMocks(this);
        this.usuarioMock = new Usuario();
        this.gasto1 = new Gasto("qwer", 10.00, Date.from(Instant.now()), Frecuencia.MENSUAL );
        this.gastos = new ArrayList<>();
        this.gastos.add(this.gasto1);
        this.usuarioMock.setGastos(gastos);
    }

    @AfterEach
    public void tearDown(){
        this.usuarioMock = null;
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
//        GestorDeGastos gestor = dadoQueExisteUnGestorConUnGastoRegistrado();
        this.sessionFactory.getCurrentSession().save(this.usuarioMock);
        boolean saveSuccessful =  this.repoGastoImpl.guardarGasto(this.usuarioMock.getId(), this.gasto1);

        assertThat(saveSuccessful, equalTo(true));
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaEliminarUnGastoEnElGestorDeGastos() throws ParseException {
        GestorDeGastos gestor = dadoQueExisteUnGestorConUnGastoRegistrado();

//         gastos = this.repositorioGestorDeGastosImpl.obtenerTodosLosGastosDeUnGestor(this.usuarioMock.getId());
//        Gasto gasto = gastos.get(0);
//        gestor.eliminarGasto(gasto);

//        this.sessionFactory.getCurrentSession().save(gestor);
        List<Gasto> gastos = this.repositorioGestorDeGastosImpl.obtenerTodosLosGastosDeUnGestor(this.usuarioMock.getId());

        assertThat(gastos.size(), equalTo(0));
    }


    @Test
    @Rollback
    @Transactional
    public void queSePuedaRegistrarOtroGastoEnElGestorDeGastos() throws ParseException {
        GestorDeGastos gestor = dadoQueExisteUnGestorConUnGastoRegistrado();

//        Gasto gastoAdicional = new Gasto();
//        gestor.registrarGasto(gastoAdicional);


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
        Date fechaVencimiento = formatoFechas.parse(fechaString);
        Gasto gasto = new Gasto("impuesto",5000, fechaVencimiento, Frecuencia.MENSUAL);
//        GestorDeGastos gestor = new GestorDeGastos();
//        gestor.registrarGasto(gasto);
        this.sessionFactory.getCurrentSession().save(new GestorDeGastos());
        return new GestorDeGastos();
    }

    /*@Test
    @Rollback
    @Transactional
    public void queSePuedaModificarUnGastoEnElGastorDeGastos(){
        Gasto gasto = new Gasto("impuesto",5000);
        ServicioGestorDeGastosImpl
    }*/

}
