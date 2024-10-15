package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioGestorDeGastosImpl;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.presentacion.UsuarioDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.empty;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.thymeleaf.util.ArrayUtils.isEmpty;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class ServicioUsuarioTest {
    private UsuarioDTO usuarioDTOMock;
    private ServicioUsuario servicioUsuario;
    private RepositorioUsuarioImpl repositorioUsuarioMock;

    @BeforeEach
    public void init(){
        repositorioUsuarioMock = mock(RepositorioUsuarioImpl.class);
        this.servicioUsuario = new ServicioUsuarioImpl(repositorioUsuarioMock);
       this.usuarioDTOMock = mock(UsuarioDTO.class);
    }
    @Test
    public void siNoHayContactosDeberiaDevolverUnaListaVacia(){
        when(usuarioDTOMock.getEmail()).thenReturn("test@unlam.edu.ar");
        dadoQueNoHayContactos();
        List<Usuario> contactos =  cuandoConsultoLosContactos(usuarioDTOMock.getEmail() );
        obtengoUnaListaVacia(contactos);
    }

    private void dadoQueNoHayContactos() {
        List<Usuario> contactos= new ArrayList<Usuario>();
        when(repositorioUsuarioMock.obtenerContactos("test@example.com")).thenReturn(contactos);    }

    private List<Usuario> cuandoConsultoLosContactos(String email) {
       return servicioUsuario.getContactos(email);
    }
    private void obtengoUnaListaVacia(List<Usuario> listado) {
        assertThat(listado.size(), equalTo(0));
    }

}
