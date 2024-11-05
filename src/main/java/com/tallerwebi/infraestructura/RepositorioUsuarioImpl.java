package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional
    @Override
    public Usuario buscarUsuario(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
        Root<Usuario> root = query.from(Usuario.class);

        query.select(root).where(
                builder.and(
                        builder.equal(root.get("email"), email),
                        builder.equal(root.get("password"), password)
                )
        );

        Usuario user = session.createQuery(query).uniqueResult();
        return user;
    }

    @Transactional
    @Override
    public boolean guardar(Usuario usuario) {

        Serializable user = sessionFactory.getCurrentSession().save(usuario);
        if (user != null) {
            return true;
        }
        return false;
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
    public List<Usuario> getContactosSugeridos(Integer miUsuarioId) {
        final Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT u FROM Usuario u " +
                "WHERE u.id != :miUsuarioId " +
                "AND u.id NOT IN (" +
                "   SELECT uu.id FROM Usuario usuario " +
                "   JOIN usuario.contactos uu " +
                "   WHERE usuario.id = :miUsuarioId" +
                ")";
        return (List<Usuario>) session.createQuery(hql)
                .setParameter("miUsuarioId", miUsuarioId)
                .list();
    }

    @Override
    public Usuario buscarUsuarioPorId(int id) {
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("id", id))

                .uniqueResult();
    }

    @Override
    public List<Usuario> buscarUsuarioPorNombre(String nombreUsuario) {
        final Session session = sessionFactory.getCurrentSession();
        return (List<Usuario>) session.createCriteria(Usuario.class)
                .add(Restrictions.like("nombre", "%" + nombreUsuario + "%"))
                .list();
    }
}
