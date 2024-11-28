package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ContactoExistente;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.interfaces.RepositorioProyeInversion;
import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.interfaces.ServicioUsuario;
import com.tallerwebi.infraestructura.ProyeInversionRepositorio;
import com.tallerwebi.presentacion.ProyeInversionDTO;
import org.hibernate.type.ZonedDateTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

    private RepositorioUsuario repositorioUsuario;
    private RepositorioProyeInversion proyectoInversionRepository;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario, RepositorioProyeInversion proyeInvRepo) {
        this.repositorioUsuario = repositorioUsuario;
        this.proyectoInversionRepository = proyeInvRepo;
    }

    @Override
    public Usuario getUsuarioById(Integer id) {
        return this.repositorioUsuario.buscarUsuarioPorId(id);
    }

    @Override
    public Boolean cargarSaldo(BigDecimal valor, int idUser) throws Exception {
        try {
            // Zona horaria de Buenos Aires
            ZoneId zoneId = ZoneId.of("America/Argentina/Buenos_Aires");

            // Fecha y hora actual en esa zona
            ZonedDateTime fechaHoraBuenosAires = ZonedDateTime.now(zoneId);

            Usuario usuario = this.repositorioUsuario.buscarUsuarioPorId(idUser);
            if (usuario == null) {
                throw new Exception("Usuario no encontrado en la base de datos");
            }
            usuario.setSaldo(usuario.getSaldo().add(valor));

            Saldo saldo = new Saldo();
            saldo.setUsuario(usuario);
            saldo.setFecha(Date.from(fechaHoraBuenosAires.toInstant()));
            saldo.setValor("+" + valor.toString());

            this.repositorioUsuario.modificar(usuario);
            this.repositorioUsuario.saveSaldoFromUser(saldo);

            return true;
        } catch (Exception e) {
            return false;
        }
    } 


    @Override
    public Boolean agregarUsuarioAContactos(Usuario usuarioQueGuarda, Usuario usuarioAGuardar) throws Exception {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(usuarioQueGuarda.getId());
        Usuario contacto = repositorioUsuario.buscarUsuarioPorId((usuarioAGuardar.getId()));
        if (usuario==null || contacto ==null){
            throw new Exception("usuario no econtrado en la bd");
        }

        // Agregar el contacto a la lista de contactos del usuario
        usuario.getContactos().add(contacto);

        // Guardar la entidad Usuario con la relación actualizada
        repositorioUsuario.modificar(usuario);
      //  repositorioUsuario.agregarContacto((usuarioQueGuarda.getId()),(usuarioAGuardar.getId()));

        return true;
    }


    @Override
    public Boolean reportarProyectoSospechoso(String motivo, int idProyect, int idUser2, int idUserQueReporta) { ///
        return null;
    }

    @Override
    public Boolean suspenderPublicacion(String motivo, int idProyectoInversion) {
        ProyectoInversion proyecto = proyectoInversionRepository.getProyectoById(idProyectoInversion);
        if (proyecto != null) {
            proyecto.setEnSuspension(true);
            proyecto.setMotivoSuspension(motivo);
            proyectoInversionRepository.updateProyeInversion(proyecto);
            return true;
        }
        return false;
    }

    @Override
    public Boolean suspenderUsuario(String motivo, int idUser) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(idUser);
        if (usuario != null) {
            usuario.setEnSuspension(true);
            usuario.setMotivoDeSuspension(motivo);
            repositorioUsuario.modificar(usuario);
            return true;
        }
        return false;
    }

    @Override
    public List<Usuario> buscarUsuarioPorNombre(String nombreUsuario) {
        return repositorioUsuario.buscarUsuarioPorNombre(nombreUsuario);
    }


    @Override
    public Boolean revertirSuspensionProyecto(int idProyectoInversion) {

        ProyectoInversion proyecto = proyectoInversionRepository.getProyectoById(idProyectoInversion);
        if (proyecto != null) {
            proyecto.setEnSuspension(false);
            proyecto.setMotivoSuspension("");
            proyectoInversionRepository.updateProyeInversion(proyecto);
            return true;
        }
        return false;
    }

    @Override
    public Boolean revertirSuspensionUsuario(int idUsuario) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(idUsuario);
        if (usuario != null) {
            usuario.setEnSuspension(false);
            usuario.setMotivoDeSuspension("");
            repositorioUsuario.modificar(usuario);
            return true;
        }
        return false;
    }

    @Override
    public Boolean eliminarPublicacion(int idProyectoInver) { /// de la lista no ?
        return null;
    }

    @Override
    public List<Usuario> getContactos(String email) {

        return repositorioUsuario.obtenerContactos(email);
    }

    @Override
    public List<Usuario> getContactosSugeridos(Integer id) {
        List<Usuario> contactos = repositorioUsuario.getContactosSugeridos(id);

        return contactos;
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer id) {
        return repositorioUsuario.buscarUsuarioPorId(id);
    }

    @Override
    public List<Usuario> getUsuariosSuspedidos() {
        List<Usuario> usuariosSuspendidos = repositorioUsuario.getUsuariosSuspendidos();
        return usuariosSuspendidos;
    }

    @Override
    public Boolean eliminarUsuarioDeContactos(Usuario usuarioQueElimina, Usuario usuarioAEliminar) {
        List<Usuario> contactos = usuarioQueElimina.getContactos();

        for (Usuario contacto : contactos) {
            if (Objects.equals(contacto.getId(), usuarioAEliminar.getId())) {
                contactos.remove(usuarioAEliminar);
                usuarioQueElimina.setContactos(contactos);
                repositorioUsuario.modificar(usuarioQueElimina);
                return true;
            }
        }
        return false;
    }

    @Override
    public void cambiarEstadoUsuario(Usuario usuario) throws Exception {
        Usuario usuarioCambio = repositorioUsuario.buscarUsuarioPorId(usuario.getId());
        if (usuarioCambio == null) {
            throw new Exception("Usuario no encontrado con id: " + usuario.getId());
        }

        usuarioCambio.setEstaActivo(!usuarioCambio.getEstaActivo());

        if(usuarioCambio.getEstaActivo() && !usuarioCambio.getEnSuspension()){ /// agrego ultima parte para limpiar motivo previo
           usuarioCambio.setMotivoDeSuspension("");
        }
        repositorioUsuario.modificar(usuarioCambio);
    }

    @Override
    public void actualizarDatos(Usuario usuario) {
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public List<ProyeInversionDTO> obtenerProyectosRecomendados(Integer usuarioId) throws Exception {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(usuarioId);

        List<ProyeInversionDTO> proyesDTO = new ArrayList<>();
        if (usuario == null) {
            throw new Exception("Usuario no encontrado con id: " + usuario.getId());
        }
        BigDecimal saldo = usuario.getSaldo();
        //los trae con tipo ProyectoInversion
        List<ProyectoInversion> proyesInv =  repositorioUsuario.getProyectosRecomendados(usuarioId, saldo);

        //y aca los convertimos para obtener la ctdad de participantes
        for(ProyectoInversion proye : proyesInv){
            ProyeInversionDTO proyeInvDTO = ProyeInversionDTO.convertToDTO(proye);
            Integer participantes = this.proyectoInversionRepository.getParticipantesFromProyecto(proye.getId());

            proyeInvDTO.setParticipantes(participantes);
            proyesDTO.add(proyeInvDTO);
        }
        return proyesDTO;
    }

    @Override
    public List<Saldo> getHistorialSaldoByIdUsuario(Integer idUsuario){
        List<Saldo> saldos = this.repositorioUsuario.getSaldosByUserId(idUsuario);
        return saldos;
    }


}
