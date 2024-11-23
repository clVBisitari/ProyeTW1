package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Frecuencia;
import com.tallerwebi.dominio.Gasto;

import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public class RepositorioGastoImpl {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioGastoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer obtenerGastosDelMes(Integer usuarioId) {
        String hql = "SELECT SUM(g.monto) FROM Gasto g WHERE g.usuario.id = :usuarioId AND MONTH(g.fecha) = MONTH(CURRENT_DATE) AND YEAR(g.fecha) = YEAR(CURRENT_DATE)";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("usuarioId", usuarioId);

        Long total = (Long) query.uniqueResult();
        return total != null ? total.intValue() : 0;
    }

    public List<Gasto> buscarGastoPorDescripcion(String descripcion) {
        String hql = "FROM Gasto WHERE descripcion = :descripcion";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("descripcion", descripcion);

        return query.getResultList();
    }

    public List<Gasto> obtenerTodosLosGastos() {
        String hql = "FROM Gasto";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        return query.getResultList();
    }

    public List<Gasto> obtenerTodosLosGastosPorUsuarioId(Integer idUsuario) {
        String hql = "FROM Gasto g WHERE g.usuario.id = :idUsuario";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("idUsuario", idUsuario);

        return query.getResultList();
    }

    public void eliminarGasto(Long id) {
        String hql = "DELETE FROM Gasto WHERE id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    @Transactional
    public void modificarValorDeUnGasto(Long id, BigDecimal valor) {
        String hql = "UPDATE Gasto g SET g.valor = :valor WHERE g.id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("valor", valor);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    public void modificarFechaDeVencimientoDeUnGasto(Long id, Date fechaVencimiento) {
        String hql = "UPDATE Gasto SET fechaVencimiento = :fechaVencimiento WHERE id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("fechaVencimiento", fechaVencimiento);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    public void modificarFechaDeRecordatorioDeUnGasto(Long id, Date fechaRecordatorio) {
        String hql = "UPDATE Gasto SET fechaRecordatorio = :fechaRecordatorio WHERE id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("fechaRecordatorio", fechaRecordatorio);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    public Gasto buscarGastoPorId(Long id) {
        String hql = "FROM Gasto WHERE id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);

        return (Gasto) query.uniqueResult();
    }

    public void modificarFrecuenciaDeUnGasto(Long id, Frecuencia frecuencia) {
        String hql = "UPDATE Gasto SET frecuencia = :frecuencia WHERE id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("frecuencia", frecuencia);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    public boolean guardarGasto(Integer userId, Gasto gasto) {

        Session session = sessionFactory.getCurrentSession();

        Usuario usuario = session.get(Usuario.class, userId);
        if (usuario == null) {
            throw new IllegalArgumentException("No se encontró un usuario con el ID proporcionado: " + userId);
        }
        gasto.setUsuario(usuario);

        Serializable gastoId = session.save(gasto);
        if(gastoId != null) return true;
        return false;
    }
}
