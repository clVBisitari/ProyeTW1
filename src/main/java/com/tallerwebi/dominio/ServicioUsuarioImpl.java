package com.tallerwebi.dominio;

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
    public Usuario buscarUsuarioPorNombre(String nombreUsuario) {
        return repositorioUsuario.buscarUsuarioPorNombre(nombreUsuario);
    }

    @Override
    public Boolean agregarUsuarioAContactos(Usuario usuarioQueGuarda, Usuario usuarioAGuardar) {
        List<Usuario>contactos = usuarioQueGuarda.getContactos();
        contactos.add(usuarioAGuardar);
        usuarioQueGuarda.setContactos(contactos);
        repositorioUsuario.modificar(usuarioQueGuarda);
        System.out.println("usuarioooooooooooooooooooooo con contactos nuevos : "+ usuarioQueGuarda);

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
    public void suspenderUsuario(String motivo, int idUser) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(idUser);
        usuario.setEnSuspencion(true);
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public List<Usuario> buscarUsuario(Usuario nombreUsuario) {
        return List.of();
    }


    @Override
    public Boolean revertirSuspensionProyecto(int idProyectoInversion) {

        return null;
    }

    @Override
    public void revertirSuspensionUsuario(int idUsuario) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(idUsuario);
        usuario.setEnSuspencion(false);
        repositorioUsuario.modificar(usuario);
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
    public List<Usuario> getContactosSugeridos(String email) {

        Usuario user = repositorioUsuario.buscar(email);
        List<Usuario> contactos = repositorioUsuario.obtenerContactos(email);
        List<Usuario> contactosSugeridos = new ArrayList<>();

        Random random = new Random();

        for (Usuario contacto : contactos) {
            List<Usuario> contactosDeContacto = contacto.getContactos();

            // Verifica si hay contactos sugeridos disponibles
            if (!contactosDeContacto.isEmpty()) {
                // Selecciona un contacto aleatorio
                Usuario contactoAleatorio = contactosDeContacto.get(random.nextInt(contactosDeContacto.size()));
                contactosSugeridos.add(contactoAleatorio);
            }
        }

        return contactosSugeridos;

    }
}
