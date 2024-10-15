package com.tallerwebi.dominio;

import com.tallerwebi.dominio.interfaces.ServicioGestorGastos;
import com.tallerwebi.infraestructura.RepositorioGestorDeGastosImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("servicioGestorDeGastos")
public class ServicioGestorDeGastosImpl implements ServicioGestorGastos {

    private RepositorioGestorDeGastosImpl repositorioGestorDeGastos;
    @Autowired
    public ServicioGestorDeGastosImpl(RepositorioGestorDeGastosImpl repositorioGestorDeGastos){
        this.repositorioGestorDeGastos = repositorioGestorDeGastos;
    }

    public void guardarGestor(GestorDeGastos gestorDeGastos){
         repositorioGestorDeGastos.guardarGestor(gestorDeGastos);
    }

    public Double actualizarTotalGastosDelMesEnCursoPorId(Long gestorId) {
        List<Gasto> gastos = this.repositorioGestorDeGastos.obtenerTodosLosGastosDeUnGestor(gestorId);
        Double gastoTotal = 0.0;
        for(int i=0; i<gastos.size(); i++ ){
            if(esGastoDelMesEnCurso(gastos.get(i).getFechaVencimiento()))
             gastoTotal += gastos.get(i).getValor();
        }
        return gastoTotal;
    }

    private boolean esGastoDelMesEnCurso(Date fechaAComparar){
        Date fechaActual = new Date();

        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar.setTime(fechaActual);
        calendar2.setTime(fechaAComparar);

        return (calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH));
    }

}
