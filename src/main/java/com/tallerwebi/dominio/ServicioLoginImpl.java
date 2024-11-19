package com.tallerwebi.dominio;

import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.interfaces.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        Usuario usuario = repositorioUsuario.buscarUsuario(email, password);
        if(usuario == null) return null;
        return usuario;
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());

        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }

        Usuario nuevoUser = new Usuario(usuario.getEmail(), usuario.getPassword(), usuario.getNombre(), usuario.getApellido(), usuario.getDni());


//        GestorDeGastos gestor = new GestorDeGastos();
//        gestor.setGastos(new ArrayList<>());
//        gestor.setUsuario(nuevoUser);

        nuevoUser.setEsAdmin(false);
        nuevoUser.setEnSuspension(false);
        nuevoUser.setSaldo(new BigDecimal(0.00));
//        nuevoUser.setGestorDeGastos(gestor);

        repositorioUsuario.guardar(nuevoUser);
    }

}

