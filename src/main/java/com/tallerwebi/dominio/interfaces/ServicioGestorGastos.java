package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.dominio.GestorDeGastos;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ServicioGestorGastos {
    Double actualizarTotalGastosDelMesEnCursoPorId(Long gestorId);
    Integer actualizarCantidadServiciosPorVencerMesEnCurso(Long gestorId);
    List<Gasto> obtenerTodosLosGastosDeUnGestor(Long id);
    void guardarGestor(GestorDeGastos gestorDeGastos);
    Boolean esGastoVencido(Date fechaVencimiento);
    Boolean esGastoDelMesEnCurso(Date fechaAComparar);
    void guardarGasto(Gasto gasto);
}
