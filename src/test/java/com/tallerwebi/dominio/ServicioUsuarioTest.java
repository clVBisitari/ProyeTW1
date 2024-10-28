package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioGestorDeGastosImpl;
import com.tallerwebi.dominio.interfaces.ServicioUsuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.presentacion.UsuarioDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.security.MessageDigest.isEqual;
import static java.util.Optional.empty;
import static java.util.function.Predicate.not;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.thymeleaf.util.ArrayUtils.isEmpty;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class ServicioUsuarioTest {
    private UsuarioDTO usuarioDTOMock;
    private ServicioUsuario servicioUsuario;
    private RepositorioUsuarioImpl repositorioUsuarioMock;

    @BeforeEach
    public void init() {
        repositorioUsuarioMock = mock(RepositorioUsuarioImpl.class);
        this.servicioUsuario = new ServicioUsuarioImpl(repositorioUsuarioMock);
        this.usuarioDTOMock = mock(UsuarioDTO.class);
    }

    @Test
    public void siHayContactosDeberiaDevolverUnaLista() {
        when(usuarioDTOMock.getEmail()).thenReturn("user@example.com");
        dadoQueHayContactos();
        List<Usuario> contactos = cuandoConsultoLosContactos(usuarioDTOMock.getEmail());
        obtengoUnaListaDeContactos(contactos);
    }

    @Test
    public void siHayContactosDevuelveUnaListaDeSugeridos() {
        Usuario user = new Usuario();
        user.setEmail("user@example.com");
        Usuario subContacto1 = new Usuario();
        Usuario subContacto2 = new Usuario();
        List<Usuario> contactos = crearListaDeContactos(user, subContacto1, subContacto2);

        when(repositorioUsuarioMock.buscar("user@example.com")).thenReturn(user);
        when(repositorioUsuarioMock.obtenerContactos("user@example.com"))
                .thenReturn(contactos);

        List<Usuario> contactosSugeridos = servicioUsuario.getContactosSugeridos("user@example.com");

        assertThat(contactosSugeridos, Matchers.is(notNullValue()));
        assertThat(contactosSugeridos, hasItem(subContacto2));
        assertThat(contactosSugeridos, hasItem(subContacto1)); // Verificar que incluye contacto2
        assertThat(contactosSugeridos, hasSize(2));
    }

    @Test
    public void siNoHayContactosDeberiaDevolverUnaListaVacia() {
        when(usuarioDTOMock.getEmail()).thenReturn("test@unlam.edu.ar");
        dadoQueNoHayContactos();
        List<Usuario> contactos = cuandoConsultoLosContactos(usuarioDTOMock.getEmail());
        obtengoUnaListaVacia(contactos);
    }

    @Test
    public void siAgregoUnContactoDebeDevolverVerdadero() {
        Usuario usuarioQueGuarda = new Usuario();
        usuarioQueGuarda.setContactos(new ArrayList<>());

        Usuario usuarioAGuardar = new Usuario();
        usuarioAGuardar.setEmail("contacto@example.com");

        Boolean resultado = servicioUsuario.agregarUsuarioAContactos(usuarioQueGuarda, usuarioAGuardar);


        assertThat(resultado, equalTo(true));
        assertThat(usuarioQueGuarda.getContactos(), hasItem(usuarioAGuardar));
        verify(repositorioUsuarioMock, times(1)).modificar(usuarioQueGuarda);
    }

    @Test
    public void dadoQueHayUnUsuarioSuspendidoSiSeRevierteLaSuspecionElUsuarioAfectadoCambiaDeEstado() {
        Usuario usuarioSuspendido = dadoQueHayUnUsuarioSuspendido();
        puedoRevertirSuspencion(usuarioSuspendido);
    }

    @Test
    public void dadoQueHayUnUsuarioNoSuspendidoSiSeSuspendeElUsuarioAfectadoCambiaDeEstado() {
        Usuario usuarioNoSuspendido = dadoQueHayUnUsuarioNoSuspendido();
        puedoSuspenderlo(usuarioNoSuspendido);
    }

    @Test
    public void siBuscoUnUsuarioPorNombreDebeDevolverElUsuario() {
        List<Usuario> usersBuscado = new ArrayList<Usuario>();
        usersBuscado.add(new Usuario());
        usersBuscado.get(0).setNombre("Diego");
        when(repositorioUsuarioMock.buscarUsuarioPorNombre(usersBuscado.get(0).getNombre())).thenReturn(usersBuscado);
       List<Usuario> userEncontrado= servicioUsuario.buscarUsuarioPorNombre("Diego");
        assertThat(userEncontrado, equalTo(usersBuscado));
    }

    private void obtengoUnaListaDeContactos(List<Usuario> contactos) {
        assertThat(contactos.size(), equalTo(2));
    }

    private void dadoQueHayContactos() {
        List<Usuario> contactos = new ArrayList<Usuario>();
        Usuario cont1 = new Usuario();
        contactos.add(cont1);
        Usuario cont2 = new Usuario();
        contactos.add(cont2);
        when(repositorioUsuarioMock.obtenerContactos("user@example.com")).thenReturn(contactos);
    }


    private List<Usuario> crearListaDeContactos(Usuario user, Usuario subContacto1, Usuario subContacto2) {
        List<Usuario> contactos = new ArrayList<>();
        Usuario contacto1 = new Usuario();
        contacto1.setEmail("contact1@example.com");
        List<Usuario> contactosContacto1 = new ArrayList<>();

        subContacto1.setEmail("subContact1@example.com");
        contactosContacto1.add(subContacto1);
        contacto1.setContactos(contactosContacto1);

        contactos.add(contacto1);
        Usuario contacto2 = new Usuario();
        contacto2.setEmail("contact2@example.com");
        contactos.add(contacto2);
        List<Usuario> contactosContacto2 = new ArrayList<>();

        subContacto2.setEmail("subContact2@example.com");
        contactosContacto2.add(subContacto2);
        contacto2.setContactos(contactosContacto2);
        user.setContactos(contactos);
        return contactos;
    }

    private void puedoSuspenderlo(Usuario usuarioNoSuspendido) {
        usuarioNoSuspendido.setEnSuspension(true);
        repositorioUsuarioMock.modificar(usuarioNoSuspendido);
        assertThat(usuarioNoSuspendido.getEnSuspension(), equalTo(true));
    }

    private Usuario dadoQueHayUnUsuarioNoSuspendido() {
        Usuario userNoSuspendido = new Usuario();
        userNoSuspendido.setEnSuspension(false);
        when(repositorioUsuarioMock.buscarUsuarioPorId(usuarioDTOMock.getId())).thenReturn(userNoSuspendido);
        return userNoSuspendido;
    }

    private void puedoRevertirSuspencion(Usuario usuarioSuspendido) {
        usuarioSuspendido.setEnSuspension(false);
        repositorioUsuarioMock.modificar(usuarioSuspendido);
        assertThat(usuarioSuspendido.getEnSuspension(), equalTo(false));
    }

    private Usuario dadoQueHayUnUsuarioSuspendido() {
        Usuario userSuspendido = new Usuario();
        userSuspendido.setEnSuspension(true);
        when(repositorioUsuarioMock.buscarUsuarioPorId(usuarioDTOMock.getId())).thenReturn(userSuspendido);
        return userSuspendido;
    }

    private void dadoQueNoHayContactos() {
        List<Usuario> contactos = new ArrayList<Usuario>();
        when(repositorioUsuarioMock.obtenerContactos("test@example.com")).thenReturn(contactos);
    }

    private List<Usuario> cuandoConsultoLosContactos(String email) {
        return servicioUsuario.getContactos(email);
    }

    private void obtengoUnaListaVacia(List<Usuario> listado) {
        assertThat(listado.size(), equalTo(0));
    }

}
