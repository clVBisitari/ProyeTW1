package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.dominio.ServicioGestorDeGastosImpl;
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
    public void queSePuedaRegistrarUnGastoEnElGestorDeGastos(){
        Gasto gasto = new Gasto("impuesto",12L);
        ServicioGestorDeGastosImpl gestor = new ServicioGestorDeGastosImpl();
        gestor.registrarGasto(gasto);
        this.sessionFactory.getCurrentSession().save(gestor);
        List<Gasto> gastos = this.repositorioGestorDeGastosImpl.obtenerTodosLosGastos(gestor.getId());

        assertThat(gastos.size(), equalTo(1));
    }

}
