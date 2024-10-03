package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.ProyeInversionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioProyectoInversion")
@Transactional
public class ServicioProyectoInversionImpl implements ServicioProyectoInversion
{

    private RepositorioProyeInversion repoProyeInversion;

    @Autowired
    public ServicioProyectoInversionImpl(RepositorioProyeInversion repoProyeInversion) {
        this.repoProyeInversion = repoProyeInversion;
    }

    @Override
    public List<ProyectoInversion> buscarProyectoInversion(String name)
    {
        List<ProyectoInversion> resultadoBusqueda = this.repoProyeInversion.getMatchProyes(name);
        return resultadoBusqueda;
    }
    @Override
    public List<ProyectoInversion> listarPublicacionesSupendidas(){
        List<ProyectoInversion> publisSuspendidas = this.repoProyeInversion.getPublicacionesSuspendidas();
        return publisSuspendidas;
    }
}
