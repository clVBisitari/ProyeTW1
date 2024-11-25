package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ContactoExistente;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.interfaces.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario getUsuarioById(Integer id) {
        return this.repositorioUsuario.buscarUsuarioPorId(id);
    }

    @Override
    public Boolean cargarSaldo(int valor) {
        return null;
    }

    @Override
    public Boolean registrarIngresoMensual(int valor) {
        return null;
    }

    @Override
    public Boolean modificarIngresoMensual(int valor) {
        return null;
    }

    @Override
    public ProyectoInversion publicarProyectoPropio(String descripción, int montoRequerido, Rubro rubro, int plazoParaInicio) {
        return null;
    }

    @Override
    public Boolean eliminarProyectoPropio(String motivo, int idProyecto) {
        return null;
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
    public Boolean invertirEnProyecto(int valor, int idProyecto) {
        return null;
    }

    @Override
    public void activarRecomendacionesAutomaticas(Double valor, int idUser) {

    }

    @Override
    public Boolean reportarProyectoSospechoso(String motivo, int idProyect, int idUser2, int idUserQueReporta) {
        return null;
    }

    @Override
    public Boolean reportarUsuarioSospechoso(String motivo, int idUserReportado, int idUserQueReporta) {
        return null;
    }

    @Override
    public Boolean suspenderPublicacion(String motivo, int proyect) {
        return null;
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

        return null;
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
    public Boolean eliminarPublicacion(int idProyectoInver) {
        return null;
    }

    @Override
    public Boolean eliminarUsuario(int idUser) {
        return null;
    }

    @Override
    public List<Usuario> getContactos(String email) {

        return repositorioUsuario.obtenerContactos(email);
    }

    @Override
    public List<Usuario> getContactosSugeridos(Integer id) {
        List<Usuario> contactos = repositorioUsuario.getContactosSugeridos(id);

        /*  List<Usuario> contactosSugeridos = new ArrayList<>();

      Random random = new Random();

        for (Usuario contacto : contactos) {
            List<Usuario> contactosDeContacto = contacto.getContactos();

            if (!contactosDeContacto.isEmpty()) {
                Usuario contactoAleatorio = contactosDeContacto.get(random.nextInt(contactosDeContacto.size()));
                if (contactoAleatorio.getId() != id) {
                    contactosSugeridos.add(contactoAleatorio);
                }
            }
        }

        return contactosSugeridos;
*/
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
}
