package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Frecuencia;
import com.tallerwebi.dominio.Gasto;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioGastoImpl {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioGastoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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

    public void eliminarGasto(Long id) {
        String hql = "DELETE FROM Gasto WHERE id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    public void modificarValorDeUnGasto(Long id, double valor) {
        String hql = "UPDATE Gasto SET valor = :valor WHERE id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("valor", valor);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    public void modificarFechaDeVencimientoDeUnGasto(Long id, String fechaVencimiento) {
        String hql = "UPDATE Gasto SET fechaVencimiento = :fechaVencimiento WHERE id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("fechaVencimiento", fechaVencimiento);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    public void modificarFechaDeRecordatorioDeUnGasto(Long id, String fechaRecordatorio) {
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
}
