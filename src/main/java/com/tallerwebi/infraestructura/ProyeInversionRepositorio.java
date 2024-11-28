package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.InversorDeProyecto;
import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.interfaces.RepositorioProyeInversion;
import com.tallerwebi.dominio.excepcion.ProyeInversionException;
import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.presentacion.InversionDTO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Transactional
@Repository("repositorioProyeInversion")
public class ProyeInversionRepositorio implements RepositorioProyeInversion
{
    private final SessionFactory sessionFactory;
    private RepositorioUsuario repoUsuario;

    @Autowired
    public ProyeInversionRepositorio(SessionFactory sessionFactory, RepositorioUsuario repoUser){
        this.sessionFactory = sessionFactory;
        this.repoUsuario = repoUser;
    }

    @Override
    public ProyectoInversion getProyectoById(Integer idProyeInversion) {
        final Session session = sessionFactory.getCurrentSession();

        ProyectoInversion  proyeInversion = session.get(ProyectoInversion.class, Math.toIntExact(idProyeInversion));

        if(proyeInversion == null) throw new ProyeInversionException("la entidad con id " + idProyeInversion + " no existe");

        return proyeInversion;
    }

    @Override
    public List<ProyectoInversion>getProyectosInversion(Integer idUsuario){
        final Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProyectoInversion> query = builder.createQuery(ProyectoInversion.class);
        Root<ProyectoInversion> root = query.from(ProyectoInversion.class);
        query.select(root)
                .where(builder.equal(root.get("titular").get("id"), idUsuario));
        return session.createQuery(query).getResultList();
    }

    @Override
    public List<ProyectoInversion> getMatchProyes(String name){
        final Session session = sessionFactory.getCurrentSession();

        String hql = "FROM ProyectoInversion WHERE LOWER(titulo) LIKE LOWER(CONCAT('%', :descripcion, '%'))";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("descripcion", name);

        return query.getResultList();
    }

    @Override
    public Integer saveProyectoInversion(ProyectoInversion proyeInversion) {
        final Session session = sessionFactory.getCurrentSession();

        Serializable idProye = session.save(proyeInversion);

        return (Integer) idProye;
    }

    @Override
    public ProyectoInversion updateProyeInversion(ProyectoInversion proyeInversion) {
        final Session session = sessionFactory.getCurrentSession();
        Object proyeResult = session.merge(proyeInversion);
        return (ProyectoInversion)proyeResult;
    }

    @Override
    public boolean deleteProyeInversion(Integer idProyeInversion) {
        try {
            final Session session = sessionFactory.getCurrentSession();
            String hql = "DELETE FROM ProyectoInversion WHERE id = :id";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setParameter("id", idProyeInversion);

            query.executeUpdate();
        } catch (HibernateException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean reportProyeInversion(Integer idProyeInversion) {
        final Session session = sessionFactory.getCurrentSession();

        ProyectoInversion proyectoInversion = session.get(ProyectoInversion.class, idProyeInversion);

        if (proyectoInversion != null)
        {
            Integer cantidadActual = proyectoInversion.getCantidadReportes();
            proyectoInversion.setCantidadReportes(cantidadActual != null ? cantidadActual + 1 : 1); // Manejar caso nulo

            session.update(proyectoInversion);
            return true;
        }
        else
        {
            throw new ProyeInversionException("El proyecto con ID " + idProyeInversion + " no existe.");
        }
    }

    @Override
    public boolean suspenderProyecto(Integer idProyeInversion) {
        final Session session =  sessionFactory.getCurrentSession();
        ProyectoInversion proye = session.get(ProyectoInversion.class, idProyeInversion);
        if(proye != null && proye.getCantidadReportes() >= 3){
            proye.setEnSuspension(true);
            session.update(proye);
            return true;
        }else{
            throw new ProyeInversionException("El proyecto con ID " + idProyeInversion + " no existe.");
        }
    }

    @Override
    public List<ProyectoInversion> getProyectosMayorInversion() {

        final Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProyectoInversion> query = builder.createQuery(ProyectoInversion.class);

        Root<ProyectoInversion> root = query.from(ProyectoInversion.class);

        query.where(builder.equal(root.get("enSuspension"), false));

        query.orderBy(builder.desc(root.get("montoRecaudado")));

        // Limitar 5
        return session.createQuery(query).setMaxResults(5).getResultList();
    }

    @Override
    public List<ProyectoInversion> getPublicacionesSuspendidas(Integer id){
        final Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProyectoInversion> query = builder.createQuery(ProyectoInversion.class);
        Root<ProyectoInversion> root = query.from(ProyectoInversion.class);
        query.select(root).where(builder.and(
                builder.equal(root.get("titular").get("id"), id),    // Condición 1: id del titular
                builder.equal(root.get("enSuspension"), true)));
        return session.createQuery(query).getResultList();
    }

    @Override
    public Integer saveInversor(InversorDeProyecto inversor, ProyectoInversion proyectoInversion, Usuario usuario){
        final Session session = sessionFactory.getCurrentSession();
        try {
            Serializable idInversor = session.save(inversor);
            BigDecimal montoFinal = proyectoInversion.getMontoRecaudado().add(inversor.getMonto());
            //actualizar el saldo del usuario
            this.repoUsuario.modificar(usuario);
            proyectoInversion.setMontoRecaudado(montoFinal);
            ProyectoInversion proyeActualizado = this.updateProyeInversion(proyectoInversion);

            return (Integer) idInversor;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<InversionDTO> getInversionesByUser(Integer userId){
        final Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT new com.tallerwebi.presentacion.InversionDTO(ip.monto, pi.id, pi.titulo)" +
                "FROM InversorDeProyecto ip " +
                "JOIN ip.proyecto pi " +
                "WHERE ip.usuario.id = :userId";
        Query query =  sessionFactory.getCurrentSession().createQuery(hql).setParameter("userId", userId);

        return query.getResultList();
    }

    @Transactional
    @Override
    public Integer getParticipantesFromProyecto(Integer proyeId){
        final Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COUNT(DISTINCT ip.usuario.id) " +
                "FROM InversorDeProyecto ip " +
                "WHERE ip.proyecto.id = :proyeId";
        Query query = session.createQuery(hql);
        query.setParameter("proyeId", proyeId);
        var result = query.getSingleResult();

        return Integer.parseInt(result.toString());
    }

}
