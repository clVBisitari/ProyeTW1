package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.GestorDeGastos;

import java.util.Date;

public interface ServicioGestorGastos {
    void guardarGestor(GestorDeGastos gestorDeGastos);
    Double actualizarTotalGastosDelMesEnCursoPorId(Long gestorId);
}
