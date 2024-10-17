package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.interfaces.RepositorioProyeInversion;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import static org.mockito.Mockito.mock;

@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepoProyeInversionTests {

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private RepositorioProyeInversion repositorioProyeInversion;

    @BeforeEach
    public void setUp() {
        this.sessionFactory = mock(SessionFactory.class);
        this.repositorioProyeInversion = new ProyeInversionRepositorio(sessionFactory);
    }

    @Test
    public void DadosDatosValidos_CuandoGuardaInversion_DevuelveObjetoguardado(){

    }
}
