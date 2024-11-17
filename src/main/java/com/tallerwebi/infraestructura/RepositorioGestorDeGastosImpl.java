package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.dominio.GestorDeGastos;
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
        String hql = "SELECT g FROM Gasto g Join GestorDeGastos s ON g.gestorId = s.id WHERE g.gestorId = :gestorDeGastosId";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("gestorDeGastosId", gestorDeGastosId);

        return query.getResultList();
    }

    public List<GestorDeGastos> obtenerTodosLosGestores() {
        String hql = "FROM GestorDeGastos";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);

        return query.getResultList();
    }

    public void guardarGestor(GestorDeGastos gestorDeGastos) {
        this.sessionFactory.getCurrentSession().save(gestorDeGastos);
    }


    //Para comparar la fecha agendada con la fecha actual
        /*Date fechaActual = new Date();
        String fechaVencimiento = "07-10-2024";
        DateFormat formateadorDeFechas = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaFormateada = formateadorDeFechas.parse(fechaVencimiento);

        assertThat(fechaActual.before(fechaFormateada), equalTo(true));*/
}
