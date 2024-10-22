package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarUsuario(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario buscar(String email) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

    @Override
    public List<Usuario> obtenerContactos(String email) {
        Usuario usuario = buscar(email);
        if (usuario != null) {
            return usuario.getContactos();
        }
        return null;
    }

    @Override
    public Usuario buscarUsuarioPorId(int id){
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("id", id))

                .uniqueResult();
    }
    @Override
    public Usuario buscarUsuarioPorNombre(String nombre){
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("nombre", nombre))
                .uniqueResult();
    }

    @Override
    public List<Usuario> buscarUsuariosPorNombre(String nombreUsuario) {
        final Session session = sessionFactory.getCurrentSession();
        return (List<Usuario>) session.createCriteria(Usuario.class)
                .add(Restrictions.like("nombre", "%" + nombreUsuario + "%"))
                .list();
    }
}
