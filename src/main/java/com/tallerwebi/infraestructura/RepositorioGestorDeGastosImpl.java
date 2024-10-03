package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Gasto;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioGestorDeGastosImpl {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioGestorDeGastosImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public List<Gasto> obtenerTodosLosGastos(Long gestorDeGastosId) {
        String hql = "FROM Gasto WHERE gestor_id = :gestor";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("gestor", gestorDeGastosId);

        return query.getResultList();
    }
}
