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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class RepositorioGestorDeGastosImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioGestorDeGastosImpl repositorioGestorDeGastosImpl;

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
    public void queSePuedaRegistrarUnGastoEnElGestorDeGastos(){
        GestorDeGastos gestor = dadoQueExisteUnGestorConUnGastoRegistrado();

        List<Gasto> gastos = this.repositorioGestorDeGastosImpl.obtenerTodosLosGastosDeUnGestor(gestor.getId());

        assertThat(gastos.size(), equalTo(1));
    }

    @Test
    @Rollback
    @Transactional
    public void queSePuedaEliminarUnGastoEnElGestorDeGastos(){
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
        GestorDeGastos gestor = dadoQueExisteUnGestorConUnGastoRegistrado();

        Gasto gastoAdicional = new Gasto();
        gestor.registrarGasto(gastoAdicional);


    }

    @Test
    @Rollback
    @Transactional
    public void queElTotalDeGastosDelMesEnCursoSeActualiceAlRegistrarNuevosGastos(){
        Gasto gasto = new Gasto("impuesto", 50000, "10-10-2024", Frecuencia.MENSUAL);
        GestorDeGastos gestor = new GestorDeGastos();
        gestor.registrarGasto(gasto);
        this.sessionFactory.getCurrentSession().save(gasto);


    }


    private GestorDeGastos dadoQueExisteUnGestorConUnGastoRegistrado() {
        Gasto gasto = new Gasto("impuesto",5000, "10-10-2024", Frecuencia.MENSUAL);
        GestorDeGastos gestor = new GestorDeGastos();
        gestor.registrarGasto(gasto);
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
