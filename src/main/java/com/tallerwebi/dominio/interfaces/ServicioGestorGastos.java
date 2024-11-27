package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.presentacion.GastoDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ServicioGestorGastos {
    BigDecimal actualizarTotalGastosDelMesEnCursoPorId(Integer usuarioId);
    Integer actualizarCantidadServiciosPorVencerMesEnCurso(Integer usuarioId);
    List<Gasto> obtenerTodosLosGastosDeUnGestor(Integer id);
    Boolean esGastoVencido(Date fechaVencimiento);
    Boolean esGastoDelMesEnCurso(Date fechaAComparar);
    Boolean guardarGasto(Integer userId, GastoDTO gastoDto);
    void eliminarGasto(Long id);
    BigDecimal obtenerGastosDelMes(Integer usuarioId);
}
