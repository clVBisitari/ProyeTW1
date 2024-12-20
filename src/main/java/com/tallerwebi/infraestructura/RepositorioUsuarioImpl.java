package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.Saldo;
import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
@Transactional
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

        return user != null;
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
            // Filtrar los contactos para incluir solo los que están activos
            return usuario.getContactos().stream()
                    .filter(Usuario::getEstaActivo) // Filtrar por usuarios activos
                    .collect(Collectors.toList());
        }
        return null;
    }
    @Override
    public List<Usuario> getContactosSugeridos(Integer miUsuarioId) {
        final Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT u FROM Usuario u " +
                "WHERE u.id != :miUsuarioId " +
                "AND u.estaActivo = true " + // Agregamos la condición para usuarios activos
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

        String hql = "FROM Usuario u WHERE u.nombre LIKE :nombreUsuario AND u.estaActivo = true";
        return (List<Usuario>) session.createQuery(hql)
                .setParameter("nombreUsuario", "%" + nombreUsuario + "%")
                .list();
    }

    @Override
    public List<Usuario> getUsuariosSuspendidos() {
        final Session session = sessionFactory.getCurrentSession();

        String hql = "FROM Usuario u WHERE u.enSuspension = true";

        return session.createQuery(hql, Usuario.class).getResultList();
    }

    @Override
    public void agregarContacto(Integer usuarioId, Integer contactoId) {
            String hql = "INSERT INTO usuario_usuarios (usuario_id, contacto_id) VALUES (:usuarioId, :contactoId)";

            Session session = sessionFactory.getCurrentSession();
            session.createNativeQuery(hql)
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("contactoId", contactoId)
                    .executeUpdate();

    }
    @Override
    public List<ProyectoInversion> getProyectosRecomendados(Integer usuarioId, BigDecimal saldo) {
        String hql = "SELECT * FROM proyecto_inversion p " +
                "WHERE p.titular_id != :usuarioId " +
                "AND :saldo > p.montorequerido * 0.1";

        Session session = sessionFactory.getCurrentSession();

        List<ProyectoInversion> proyectos = session.createNativeQuery(hql, ProyectoInversion.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("saldo", saldo)
                .getResultList();

        return proyectos;
    }

    @Override
    public List<Saldo> getSaldosByUserId(Integer idUsuario){
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT * FROM saldo s " +
                "WHERE s.usuario_id = :idUsuario ";

        List<Saldo> saldos = session.createNativeQuery(hql, Saldo.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();
        return saldos;
    }
    @Override
    public boolean saveSaldoFromUser(Saldo saldo){
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(saldo);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
