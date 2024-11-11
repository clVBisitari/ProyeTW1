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
import static org.hamcrest.Matchers.empty;

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
        user.setId(1);
        List<Usuario> contactos = crearListaDeContactos(user);

//        when(repositorioUsuarioMock.buscar("user@example.com")).thenReturn(user);
//        when(repositorioUsuarioMock.obtenerContactos("user@example.com"))
//                .thenReturn(contactos);
        when(repositorioUsuarioMock.getContactosSugeridos(user.getId())).thenReturn(contactos);

        List<Usuario> contactosSugeridos = servicioUsuario.getContactosSugeridos(1);

        assertThat(contactosSugeridos, Matchers.is(notNullValue()));
        assertThat(contactosSugeridos.get(1).getId(), comparesEqualTo(contactos.get(1).getId()));
        assertThat(contactosSugeridos.get(0).getId(), comparesEqualTo(contactos.get(0).getId())); // Verificar que incluye contacto2
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
    public void siAgregoUnContactoDebeDevolverVerdadero() throws Exception {
        Usuario usuarioQueGuarda = new Usuario();
        usuarioQueGuarda.setContactos(new ArrayList<>());
        usuarioQueGuarda.setId(1);

        Usuario usuarioAGuardar = new Usuario();
        usuarioAGuardar.setEmail("contacto@example.com");
        usuarioAGuardar.setId(2);
        when(servicioUsuario.buscarUsuarioPorId(usuarioQueGuarda.getId())).thenReturn(usuarioQueGuarda);
        when(servicioUsuario.buscarUsuarioPorId(usuarioAGuardar.getId())).thenReturn(usuarioAGuardar);

        Boolean resultado = servicioUsuario.agregarUsuarioAContactos(usuarioQueGuarda, usuarioAGuardar);


        assertThat(resultado, equalTo(true));
        assertThat(usuarioQueGuarda.getContactos(), hasItem(usuarioAGuardar));
        verify(repositorioUsuarioMock, times(1)).modificar(usuarioQueGuarda);
    }

    @Test
    public void dadoQueHayUnUsuarioSuspendidoSiSeRevierteLaSuspensionElUsuarioAfectadoCambiaDeEstado() {
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
        List<Usuario> usersBuscados = new ArrayList<Usuario>();
        usersBuscados.add(new Usuario());
        usersBuscados.get(0).setNombre("Diego");
        when(repositorioUsuarioMock.buscarUsuarioPorNombre(usersBuscados.get(0).getNombre())).thenReturn(usersBuscados);
       List<Usuario> userEncontrado= servicioUsuario.buscarUsuarioPorNombre("Diego");
        assertThat(userEncontrado, equalTo(usersBuscados));
    }

   @Test
   public void siBuscoUsuariosSuspendidosYHayObtengoUnaListaConUsuariosSuspendidos(){
       List<Usuario> usersBuscados = new ArrayList<Usuario>();
       usersBuscados.add(new Usuario());
       usersBuscados.add(new Usuario());
       usersBuscados.get(0).setNombre("Diego");
       usersBuscados.get(0).setEnSuspension(true);
       usersBuscados.get(1).setNombre("Marco");
       usersBuscados.get(1).setEnSuspension(true);
       when(repositorioUsuarioMock.getUsuariosSuspendidos()).thenReturn(usersBuscados);
       List<Usuario> usersEncontrados= servicioUsuario.getUsuariosSuspedidos();
       assertThat(usersEncontrados, equalTo(usersBuscados));
   }
    @Test
    public void siBuscoUsuariosSuspendidosYNoHayObtengoUnaListaVacia(){
        List<Usuario> usersBuscados = new ArrayList<Usuario>();
        when(repositorioUsuarioMock.getUsuariosSuspendidos()).thenReturn(usersBuscados);
        List<Usuario> usersEncontrados= servicioUsuario.getUsuariosSuspedidos();
        assertThat(usersEncontrados, hasSize(0));
    }

    @Test
    public void siDesactivoUnUsuarioSuEstadoActivoCambiaAFalse(){
        Usuario usuarioActivo = dadoQueHayUnUsuarioActivo();
        puedoCambiarElEstadoAFalse(usuarioActivo);
    }

    @Test
    public void siActivoUnUsuarioSuEstadoActivoCambiaATrue(){
        Usuario usuarioNoActivo = dadoQueHayUnUsuarioNoActivo();
        puedoCambiarElEstadoATrue(usuarioNoActivo);
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

    private List<Usuario> crearListaDeContactos(Usuario user) {
        List<Usuario> contactos = new ArrayList<>();
        Usuario contacto1 = new Usuario();
        contacto1.setId(2);
        contacto1.setEmail("contact1@example.com");
        contactos.add(contacto1);

        Usuario contacto2 = new Usuario();
        contacto2.setId(3);
        contacto2.setEmail("contact2@example.com");
        contactos.add(contacto2);

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
    private void puedoCambiarElEstadoAFalse(Usuario usuarioActivo) {
        usuarioActivo.setEstaActivo(false);
        repositorioUsuarioMock.modificar(usuarioActivo);
        assertThat(usuarioActivo.getEstaActivo(), equalTo(false));
    }

    private Usuario dadoQueHayUnUsuarioActivo() {
        Usuario userActivo = new Usuario();
        userActivo.setEstaActivo(true);
        when(repositorioUsuarioMock.buscarUsuarioPorId(usuarioDTOMock.getId())).thenReturn(userActivo);
        return userActivo;
    }
    private Usuario dadoQueHayUnUsuarioNoActivo() {
        Usuario userNoActivo = new Usuario();
        userNoActivo.setEstaActivo(false);
        when(repositorioUsuarioMock.buscarUsuarioPorId(usuarioDTOMock.getId())).thenReturn(userNoActivo);
        return userNoActivo;
    }

    private void puedoCambiarElEstadoATrue(Usuario usuarioNoActivo) {
        usuarioNoActivo.setEstaActivo(true);
        repositorioUsuarioMock.modificar(usuarioNoActivo);
        assertThat(usuarioNoActivo.getEstaActivo(), equalTo(true));
    }

}
