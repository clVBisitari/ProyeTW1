package com.tallerwebi.infraestructura;

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
}
