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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class RepositorioUsuarioTest{

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioUsuario repositorioUsuario;
    private DateFormat formatoFechas = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void init(){
        this.repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioUsuarioYPidoLosContactosDelUsuarioLaBaseDeDatosMeLosDevuelve(){

        Usuario usuario = new Usuario();

        List<Usuario>contactos = new ArrayList<>();
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

        // Ejecutar la consulta y obtener los contactos
        List<Usuario> contactosRecuperados = query.getResultList();

        // Afirmaciones para comprobar que los contactos fueron recuperados correctamente
        assertThat(contactosRecuperados, is(notNullValue()));
        assertThat(contactosRecuperados.size(), is(3));
        assertThat(contactosRecuperados, containsInAnyOrder(contacto1, contacto2, contacto3));

    }

}
