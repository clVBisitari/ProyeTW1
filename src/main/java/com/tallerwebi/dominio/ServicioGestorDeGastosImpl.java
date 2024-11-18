package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioGastoImpl;
import com.tallerwebi.dominio.interfaces.ServicioGestorGastos;
import com.tallerwebi.infraestructura.RepositorioGestorDeGastosImpl;
import com.tallerwebi.presentacion.GastoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service("servicioGestorDeGastos")
@Transactional
public class ServicioGestorDeGastosImpl implements ServicioGestorGastos {

    private RepositorioGestorDeGastosImpl repositorioGestorDeGastos;
    private RepositorioGastoImpl repositorioGasto;

    @Autowired
    public ServicioGestorDeGastosImpl(RepositorioGestorDeGastosImpl repositorioGestorDeGastos, RepositorioGastoImpl repositorioGasto){
        this.repositorioGestorDeGastos = repositorioGestorDeGastos;
        this.repositorioGasto = repositorioGasto;
    }

    public void guardarGestor(GestorDeGastos gestorDeGastos){
         repositorioGestorDeGastos.guardarGestor(gestorDeGastos);
    }

    public void guardarGasto(Gasto gasto){
        repositorioGasto.guardarGasto(gasto);
    }

    @Override
    public GestorDeGastos buscarPorUsuario(Integer id) {
        return repositorioGestorDeGastos.buscarGestorPorUsuario(id);
    }

    public Double actualizarTotalGastosDelMesEnCursoPorId(Long gestorId) {

        List<Gasto> gastos = this.repositorioGestorDeGastos.obtenerTodosLosGastosDeUnGestor(gestorId);

        double gastoTotal = 0.0;

        for (Gasto gasto : gastos) {
            if (esGastoDelMesEnCurso(gasto.getFechaVencimiento()))
                gastoTotal += gasto.getValor();
        }

        return gastoTotal;
    }

    public Integer actualizarCantidadServiciosPorVencerMesEnCurso(Long gestorId){
        List<Gasto> gastos = this.repositorioGestorDeGastos.obtenerTodosLosGastosDeUnGestor(gestorId);

        int cantidad = 0;

        for (Gasto gasto : gastos) {
            if ((!esGastoVencido(gasto.getFechaVencimiento())) && esGastoDelMesEnCurso(gasto.getFechaVencimiento())) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public List<GastoDTO> obtenerTodosLosGastosDeUnGestor(Long id){

        List<Gasto> gastos = repositorioGestorDeGastos.obtenerTodosLosGastosDeUnGestor(id);

        return gastos.stream().map(GastoDTO::convertirGastoaDTO).collect(Collectors.toList());

    }

    public Boolean esGastoVencido(LocalDate fechaVencimiento) {
        LocalDate fechaActualSinHora = LocalDate.now();

        return fechaVencimiento.isBefore(fechaActualSinHora);
    }

    public Boolean esGastoDelMesEnCurso(LocalDate fechaAComparar) {
        LocalDate fechaActual = LocalDate.now();

        return (fechaActual.getYear() == fechaAComparar.getYear() &&
                fechaActual.getMonth() == fechaAComparar.getMonth());
    }

}
