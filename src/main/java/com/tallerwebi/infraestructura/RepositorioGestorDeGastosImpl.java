package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Gasto;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    public List<Gasto> obtenerTodosLosGastosPorUsuario(Integer usuarioId) {
        String hql = "FROM Gasto WHERE usuario.id = :usuarioId";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);

        return query.getResultList();
    }

    public BigDecimal obtenerGastosDelMes(Integer usuarioId) {
        String hql = "SELECT SUM(g.valor) FROM Gasto g WHERE g.usuario.id = :usuarioId AND MONTH(g.fechaVencimiento) = MONTH(CURRENT_DATE) AND YEAR(g.fechaVencimiento) = YEAR(CURRENT_DATE)";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("usuarioId", usuarioId);

        BigDecimal total = (BigDecimal) query.uniqueResult();
        return total != null ? total : BigDecimal.valueOf(0);
    }

    //Para comparar la fecha agendada con la fecha actual
        /*Date fechaActual = new Date();
        String fechaVencimiento = "07-10-2024";
        DateFormat formateadorDeFechas = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaFormateada = formateadorDeFechas.parse(fechaVencimiento);

        assertThat(fechaActual.before(fechaFormateada), equalTo(true));*/
}
