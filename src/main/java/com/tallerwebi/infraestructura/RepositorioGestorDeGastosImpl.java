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

    public List<Gasto> obtenerTodosLosGastosDeUnGestor(Integer usuarioId) {
        String hql = "FROM Gasto g WHERE g.usuario.id = :usuarioId";
        List<Gasto> gastos = sessionFactory.getCurrentSession().createQuery(hql, Gasto.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
        return gastos;
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
