package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.ProyectoInversion;
import com.tallerwebi.dominio.RepositorioProyeInversion;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("repositorioProyeInversion")
public class ProyeInversionRepositorio implements RepositorioProyeInversion
{
    private SessionFactory sessionFactory;
    @Autowired
    public ProyeInversionRepositorio(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<ProyectoInversion> getMatchProyes(String name){
        final Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProyectoInversion> query = builder.createQuery(ProyectoInversion.class);

        // Definir la raíz de la consulta (la entidad de la cual obtendremos los datos)
        Root<ProyectoInversion> root = query.from(ProyectoInversion.class);
        // Seleccionar toda la entidad
        query.select(root);
        // Ejecutar la consulta y obtener los resultados
        List<ProyectoInversion> proyectos = session.createQuery(query).getResultList();
        return proyectos;
    }

    @Override
    public List<ProyectoInversion> getPublicacionesSuspendidas(){
        final Session session = sessionFactory.getCurrentSession();

        List<ProyectoInversion> proyectoInversions = (List<ProyectoInversion>) session.createCriteria(ProyectoInversion.class)
                .uniqueResult();
        return proyectoInversions;
    }


}
