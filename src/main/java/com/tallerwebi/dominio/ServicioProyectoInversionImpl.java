package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ProyeInversionException;
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
    public List<ProyectoInversion> getProyectosUsuario(Integer idUsuario) {
        List<ProyectoInversion> proyectos = null;
        try {
            proyectos = this.repoProyeInversion.getProyectosInversion(idUsuario);
        } catch (ProyeInversionException e) {
            throw e;
        }
        return proyectos;
    }

    @Override
    public List<ProyectoInversion> buscarProyectoInversion(String name)
    {
        List<ProyectoInversion> resultadoBusqueda = this.repoProyeInversion.getMatchProyes(name);
        return resultadoBusqueda;
    }

    @Override
    public Long guardarProyectoInversion(ProyectoInversion proyeInversion) {
        return this.repoProyeInversion.saveProyectoInversion(proyeInversion);
    }

    @Override
    public ProyectoInversion editarProyectoInversion(ProyectoInversion proyeInversion) {
        ProyectoInversion result = null;
        result = this.repoProyeInversion.updateProyeInversion(proyeInversion);
        return result;
    }

    @Override
    public boolean borrarProyectoInversion(Long idProyeInversion) {
        boolean deleteExitoso = this.repoProyeInversion.deleteProyeInversion(idProyeInversion);
        return deleteExitoso;
    }

    @Override
    public boolean reportarProyecto(Long idProyeInversion) {
        boolean reportExitoso = this.repoProyeInversion.reportProyeInversion(idProyeInversion);
        return reportExitoso;
    }

    @Override
    public boolean suspenderProyecto(Long idProyeInversion) {
        try {
            ProyectoInversion proyePorSuspender = this.repoProyeInversion.getProyectoById(idProyeInversion);
            if(proyePorSuspender.getCantidadReportes() >= 3){
                boolean suspensionExitosa = this.repoProyeInversion.suspenderProyecto(idProyeInversion);
                return suspensionExitosa;
            }
        } catch (ProyeInversionException e) {
            throw e;
        }
        return false;
    }

    @Override
    public List<ProyectoInversion> listarPublicacionesSupendidas(Integer idUsuario){
        List<ProyectoInversion> publisSuspendidas = this.repoProyeInversion.getPublicacionesSuspendidas(idUsuario);
        return publisSuspendidas;
    }
}
