package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.dominio.ServicioGestorDeGastosImpl;
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

    public List<Gasto> obtenerTodosLosGastosDeUnGestor(Long gestorDeGastosId) {
        //String hql = "FROM Gasto WHERE gestorId = :gestorDeGastosId";
        //String hql = "SELECT g FROM Gasto g JOIN ServicioGestorDeGastosImpl s ON g.gestor.id = s.id WHERE g.gestor.id = :gestorDeGastosId";
        //String hql = "SELECT g FROM Gasto g JOIN ServicioGestorDeGastosImpl  s ON g.gestorId = s.id WHERE g.gestorId = :gestorDeGastosId";
        String hql = "SELECT g FROM Gasto g Join ServicioGestorDeGastosImpl s ON g.gestor.id = s.id WHERE g.gestor.id = :gestorDeGastosId";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("gestorDeGastosId", gestorDeGastosId);

        return query.getResultList();
    }

    public List<ServicioGestorDeGastosImpl> obtenerTodosLosGestores() {
        String hql = "FROM ServicioGestorDeGastosImpl";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);

        return query.getResultList();
    }


    //Para comparar la fecha agendada con la fecha actual
        /*Date fechaActual = new Date();
        String fechaVencimiento = "07-10-2024";
        DateFormat formateadorDeFechas = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaFormateada = formateadorDeFechas.parse(fechaVencimiento);

        assertThat(fechaActual.before(fechaFormateada), equalTo(true));*/
}
