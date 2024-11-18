package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.dominio.GestorDeGastos;
import com.tallerwebi.presentacion.GastoDTO;

import java.time.LocalDate;
import java.util.List;

public interface ServicioGestorGastos {

    Double actualizarTotalGastosDelMesEnCursoPorId(Long gestorId);

    Integer actualizarCantidadServiciosPorVencerMesEnCurso(Long gestorId);

    List<GastoDTO> obtenerTodosLosGastosDeUnGestor(Long id);

    void guardarGestor(GestorDeGastos gestorDeGastos);

    Boolean esGastoVencido(LocalDate fechaVencimiento);

    Boolean esGastoDelMesEnCurso(LocalDate fechaAComparar);

    void guardarGasto(Gasto gasto);

    GestorDeGastos buscarPorUsuario(Integer id);

}
