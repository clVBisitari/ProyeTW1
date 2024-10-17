package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class RepositorioUsuarioTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioUsuario repositorioUsuario;
    private DateFormat formatoFechas = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void init() {
        this.repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioUsuarioYPidoLosContactosDelUsuarioLaBaseDeDatosLosDevuelve() {

        Usuario usuario = new Usuario();

        List<Usuario> contactos = new ArrayList<>();
        Usuario contacto1 = new Usuario();
        this.repositorioUsuario.guardar(contacto1);

        Usuario contacto2 = new Usuario();
        this.repositorioUsuario.guardar(contacto2);

        Usuario contacto3 = new Usuario();
        this.repositorioUsuario.guardar(contacto3);


        contactos.add(contacto1);
        contactos.add(contacto2);
        contactos.add(contacto3);
        usuario.setContactos(contactos);

        this.repositorioUsuario.guardar(usuario);
        int idUsuario = usuario.getId();

        String hql = "SELECT u.contactos FROM Usuario u WHERE u.id = :idUsuario";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("idUsuario", idUsuario);

        List<Usuario> contactosRecuperados = query.getResultList();

        assertThat(contactosRecuperados, is(notNullValue()));
        assertThat(contactosRecuperados.size(), is(3));
        assertThat(contactosRecuperados, containsInAnyOrder(contacto1, contacto2, contacto3));

    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioUsuarioYElUsuarioCargaSaldoElSaldoSeActualizaEnLaBaseDeDatos() {

        Usuario usuario = new Usuario();
        usuario.setSaldo(100.00);
        this.repositorioUsuario.guardar(usuario);
        Double saldoAAgregar = 251.00;
        usuario.setSaldo(usuario.getSaldo() + saldoAAgregar);
        this.repositorioUsuario.modificar(usuario);

        String hql = "SELECT u FROM Usuario u WHERE u.saldo = :saldo";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("saldo", 351.00);

        Usuario usuarioBD = (Usuario) query.getSingleResult();

        assertThat(usuarioBD.getSaldo(), equalTo(351.00));
        assertThat(usuarioBD, equalTo(usuario));

    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioUsuarioCuandoBuscoUnUsuarioEnLaBaseDeDatosPorEmailYPasswordDevuelveElUsuarioBuscado() {

        Usuario userBuscado = new Usuario();
        userBuscado.setEmail("diego@hotmail.com");
        userBuscado.setPassword("123456");
        this.repositorioUsuario.guardar(userBuscado);

        String hql = "SELECT u FROM Usuario u WHERE u.email = :emailUsuario AND u.password = :passwordUsuario";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("emailUsuario", "diego@hotmail.com");
        query.setParameter("passwordUsuario", "123456");

        Usuario usuarioEncontradoEnBD = (Usuario) query.getSingleResult();
        Usuario usuarioEncontradoPorRepo = repositorioUsuario.buscarUsuario("diego@hotmail.com", "123456");

        assertThat(usuarioEncontradoEnBD, equalTo(usuarioEncontradoPorRepo));
        assertThat(usuarioEncontradoPorRepo.getEmail(), equalTo("diego@hotmail.com"));
        assertThat(usuarioEncontradoPorRepo.getPassword(), equalTo("123456"));

    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioUsuarioCuandoBuscoUnUsuarioEnLaBaseDeDatosSoloPorEmailDevuelveElUsuarioBuscado() {

        Usuario userBuscado = new Usuario();
        userBuscado.setEmail("diego@hotmail.com");
        this.repositorioUsuario.guardar(userBuscado);

        String hql = "SELECT u FROM Usuario u WHERE u.email = :emailUsuario";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("emailUsuario", "diego@hotmail.com");

        Usuario usuarioEncontradoEnBD = (Usuario) query.getSingleResult();
        Usuario usuarioEncontradoPorRepo = repositorioUsuario.buscar("diego@hotmail.com");

        assertThat(usuarioEncontradoEnBD, equalTo(usuarioEncontradoPorRepo));
        assertThat(usuarioEncontradoPorRepo.getEmail(), equalTo("diego@hotmail.com"));

    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioUsuarioCuandoBuscoUnUsuarioPorIdEnLaBaseDeDatosYLoEncuentraRetornaElUsuario() {
        Usuario userBuscado = new Usuario();
        userBuscado.setId(1);
        userBuscado.setEmail("diego@hotmail.com");

        this.repositorioUsuario.guardar(userBuscado);

        Usuario userBuscado1 = new Usuario();
        userBuscado1.setEmail("diego1@hotmail.com");
        userBuscado1.setId(2);
        this.repositorioUsuario.guardar(userBuscado1);

        String hql = "SELECT u FROM Usuario u WHERE u.id = :idUsuario";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("idUsuario", 2);

        Usuario usuarioEncontradoEnBD = (Usuario) query.getSingleResult();
        Usuario usuarioEncontradoPorRepo = repositorioUsuario.buscarUsuarioPorId(userBuscado1.getId());

        assertThat(usuarioEncontradoEnBD, equalTo(usuarioEncontradoPorRepo));
        assertThat(usuarioEncontradoPorRepo.getEmail(), equalTo("diego1@hotmail.com"));
        assertThat(usuarioEncontradoPorRepo.getId(), equalTo(2));
    }
    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioUsuarioCuandoBuscoUnUsuarioPorNombreEnLaBaseDeDatosYLoEncuentraRetornaElUsuario() {
        Usuario userBuscado = new Usuario();
        userBuscado.setNombre("diego");
        this.repositorioUsuario.guardar(userBuscado);

        String hql = "SELECT u FROM Usuario u WHERE u.nombre = :nombreUsuario";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombreUsuario", "diego");

        Usuario usuarioEncontradoEnBD = (Usuario) query.getSingleResult();
        Usuario usuarioEncontradoPorRepo = repositorioUsuario.buscarUsuarioPorNombre("diego");

        assertThat(usuarioEncontradoEnBD, equalTo(usuarioEncontradoPorRepo));
        assertThat(usuarioEncontradoPorRepo.getNombre(), equalTo("diego"));
    }



}

