package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.dominio.GestorDeGastos;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ServicioGestorGastos {
    Double actualizarTotalGastosDelMesEnCursoPorId(Integer usuarioId);
    Integer actualizarCantidadServiciosPorVencerMesEnCurso(Integer usuarioId);
    List<Gasto> obtenerTodosLosGastosDeUnGestor(Integer id);
    void guardarGestor(GestorDeGastos gestorDeGastos);
    Boolean esGastoVencido(Date fechaVencimiento);
    Boolean esGastoDelMesEnCurso(Date fechaAComparar);
    Boolean guardarGasto(Integer userId, Gasto gasto);
}
