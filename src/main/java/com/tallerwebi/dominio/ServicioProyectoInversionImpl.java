package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ProyeInversionException;
import com.tallerwebi.dominio.interfaces.RepositorioProyeInversion;
import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.interfaces.ServicioProyectoInversion;
import com.tallerwebi.presentacion.InversionDTO;
import com.tallerwebi.presentacion.InversorDeProyectoDTO;
import com.tallerwebi.presentacion.ProyeInversionDTO;
import com.tallerwebi.presentacion.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("servicioProyectoInversion")
@Transactional
public class ServicioProyectoInversionImpl implements ServicioProyectoInversion
{

    private RepositorioProyeInversion repoProyeInversion;
    private RepositorioUsuario repoUsuario;


    @Autowired
    public ServicioProyectoInversionImpl(RepositorioProyeInversion repoProyeInversion, RepositorioUsuario repoUsuario) {
        this.repoProyeInversion = repoProyeInversion;
        this.repoUsuario = repoUsuario;
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
//        proyeInversion.setInversores(new InversorDeProyecto());
        proyeInversion.setCantidadReportes(0);
        proyeInversion.setEnSuspension(false);
        proyeInversion.setMontoRecaudado(new BigDecimal(0));
        proyeInversion.setPlazoParaInicio(LocalDate.of(proyeInversion.getPlazoParaInicio().getYear(), proyeInversion.getPlazoParaInicio().getMonth(), proyeInversion.getPlazoParaInicio().getDayOfMonth()));
        proyeInversion.setPlazoParaFinal(LocalDate.of(proyeInversion.getPlazoParaFinal().getYear(), proyeInversion.getPlazoParaFinal().getMonth(), proyeInversion.getPlazoParaFinal().getDayOfMonth()));

        return this.repoProyeInversion.saveProyectoInversion(proyeInversion);
    }

    @Override
    public ProyectoInversion editarProyectoInversion(ProyectoInversion proyeInversion) {
        ProyectoInversion result = null;
        result = this.repoProyeInversion.updateProyeInversion(proyeInversion);
        return result;
    }

    @Override
    public boolean borrarProyectoInversion(Integer idProyeInversion) {
        boolean deleteExitoso = this.repoProyeInversion.deleteProyeInversion(idProyeInversion);
        return deleteExitoso;
    }

    @Override
    public boolean reportarProyecto(Integer idProyeInversion) {
        boolean reportExitoso = this.repoProyeInversion.reportProyeInversion(idProyeInversion);
        return reportExitoso;
    }

    @Override
    public boolean suspenderProyecto(Integer idProyeInversion) {
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

    @Override
    public List<ProyectoInversion> getProyectosMayorInversion() {
        return this.repoProyeInversion.getProyectosMayorInversion();
    }

    @Override
    public ProyectoInversion getProyectoInversionPorId(Integer id) {
        return this.repoProyeInversion.getProyectoById(id);
    }

    @Override
    public Integer invertirEnProyecto(InversorDeProyectoDTO inversorDto){
        InversorDeProyecto inversor = InversorDeProyecto.convertToInversorDeProyecto(inversorDto);
        LocalDateTime fechaLocal = LocalDateTime.now();

        Integer idUsuario = Integer.parseInt(inversorDto.idUsuario);
//        Integer idProye = Integer.parseInt(inversorDto.idProyecto);
        BigDecimal monto = new BigDecimal(inversorDto.monto);
        Usuario usuarioValidado = this.repoUsuario.buscarUsuarioPorId(idUsuario);
        // Zona horaria de Buenos Aires
        ZoneId zoneId = ZoneId.of("America/Argentina/Buenos_Aires");

        // Fecha y hora actual en esa zona
        ZonedDateTime fechaHoraBuenosAires = ZonedDateTime.now(zoneId);

        if(usuarioValidado == null) return 0;
        //si el monto del usuario que quiere es menor a su saldo
        if(monto.compareTo(usuarioValidado.getSaldo()) > 0) return 0;
        Saldo saldo = new Saldo();
        saldo.setFecha(Date.from(fechaHoraBuenosAires.toInstant()));
        saldo.setValor("-" + monto.toString());
        saldo.setUsuario(usuarioValidado);

        this.repoUsuario.saveSaldoFromUser(saldo);

        usuarioValidado.setSaldo(usuarioValidado.getSaldo().subtract(monto));

        ProyectoInversion proyectoInversion = this.repoProyeInversion.getProyectoById(inversorDto.idProyecto);
        if(Objects.equals(usuarioValidado.getId(), idUsuario) && Objects.equals(proyectoInversion.getId(), inversorDto.idProyecto)){
            inversor.setProyecto(proyectoInversion);
            inversor.setUsuario(usuarioValidado);
        }
        Integer idInversor = this.repoProyeInversion.saveInversor(inversor, proyectoInversion, usuarioValidado);

        return idInversor;
    }

    @Override
    public List<InversionDTO>getInversionesPorUsuario(Integer userId){
        List<InversionDTO> inversionesPropias = this.repoProyeInversion.getInversionesByUser(userId);
        return inversionesPropias;
    }
}
