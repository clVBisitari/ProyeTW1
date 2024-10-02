package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioProyectoInversion")
@Transactional
public class ServicioProyectoInversionImpl {

    private ProyeInversionRepositorio repoProyeInversion;

    @Autowired
    public ServicioProyectoInversionImpl(ProyeInversionRepositorio repoProyeInversion) {
        this.repoProyeInversion = repoProyeInversion;
    }

    @Override
    public List<ProyectoInversion> buscarProyectoInversion(String name)
    {
        List<ProyectoInversion> resultadoBusqueda = this.repoProyeInversion.getMatchProyes(name);
        return resultadoBusqueda;
    }
}
