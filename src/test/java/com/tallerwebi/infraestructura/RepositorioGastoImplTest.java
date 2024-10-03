package com.tallerwebi.infraestructura;

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

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class RepositorioGastoImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioGastoImpl repositorioGastoImpl;

    @BeforeEach
    public void init(){
        this.repositorioGastoImpl = new RepositorioGastoImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnGastoEnElGestorDeGastos(){
        Gasto gasto = new Gasto("impuesto", 5000);
        this.sessionFactory.getCurrentSession().save(gasto);

        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();

        assertThat(gastos.size(), equalTo(1));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaModificarElValorDeUnGastoExistente(){
        Gasto gasto = new Gasto("impuesto", 5000);
        this.sessionFactory.getCurrentSession().save(gasto);

        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();
        Gasto gastoAModificar = gastos.get(0);
        gastoAModificar.setValor(7000);

        assertThat(gastoAModificar.getValor(), equalTo((double)7000));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaEliminarUnGastoExistente(){
        Gasto gasto = new Gasto("impuesto", 5000);
        this.sessionFactory.getCurrentSession().save(gasto);

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
    public void QueSePuedaBuscarUnGastoPorDescripcion(){
        Gasto gasto = new Gasto("impuesto", 5000);
        this.sessionFactory.getCurrentSession().save(gasto);

        List<Gasto> gastos = this.repositorioGastoImpl.buscarGastoPorDescripcion("impuesto");

        assertThat(gastos.size(), equalTo(1));
    }

}

