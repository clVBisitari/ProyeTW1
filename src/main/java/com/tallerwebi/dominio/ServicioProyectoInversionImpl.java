package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ProyeInversionException;
import com.tallerwebi.dominio.interfaces.RepositorioProyeInversion;
import com.tallerwebi.dominio.interfaces.ServicioProyectoInversion;
import com.tallerwebi.presentacion.ProyeInversionDTO;
import com.tallerwebi.presentacion.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
    public Integer guardarProyectoInversion(ProyeInversionDTO proyeInversionDTO, UsuarioDTO usuario) {
        Usuario user = usuario.convertToUsuario();
        ProyectoInversion proyeInversion = proyeInversionDTO.convertToProyectoInversion();
        proyeInversion.setTitular(user);
        proyeInversion.setInversores(new ArrayList<Usuario>());
        proyeInversion.setCantidadReportes(0);
        proyeInversion.setEnSuspension(false);
        proyeInversion.setMontoRecaudado(0);
//        proyeInversion.setPlazoParaInicio(LocalDate.of(proyeInversion.getPlazoParaInicio().cdate.getYear(), proyeInversion.getPlazoParaInicio().cdate.getMonth(), proyeInversion.getPlazoParaInicio().cdate.getDayOfMonth()));
//        proyeInversion.setPlazoParaFinal(LocalDate.of(proyeInversion.getPlazoParaFinal().getYear(), proyeInversion.getPlazoParaFinal().getMonth(), proyeInversion.getPlazoParaFinal().getDayOfMonth()));

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
        Integer idProyeInteger = idProyeInversion.intValue();
        boolean deleteExitoso = this.repoProyeInversion.deleteProyeInversion(idProyeInteger);
        return deleteExitoso;
    }

    @Override
    public boolean reportarProyecto(Long idProyeInversion) {
        Integer idProyeInteger = idProyeInversion.intValue();
        boolean reportExitoso = this.repoProyeInversion.reportProyeInversion(idProyeInteger);
        return reportExitoso;
    }

    @Override
    public boolean suspenderProyecto(Long idProyeInversion) {
        Integer idProyeInteger = idProyeInversion.intValue();
        try {
            ProyectoInversion proyePorSuspender = this.repoProyeInversion.getProyectoById(idProyeInteger);
            if(proyePorSuspender.getCantidadReportes() >= 3){
                boolean suspensionExitosa = this.repoProyeInversion.suspenderProyecto(idProyeInteger);
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
