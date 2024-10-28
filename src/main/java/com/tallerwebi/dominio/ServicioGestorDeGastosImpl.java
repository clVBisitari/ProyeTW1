package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioGastoImpl;
import com.tallerwebi.dominio.interfaces.ServicioGestorGastos;
import com.tallerwebi.infraestructura.RepositorioGestorDeGastosImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public Double actualizarTotalGastosDelMesEnCursoPorId(Long gestorId) {
        List<Gasto> gastos = this.repositorioGestorDeGastos.obtenerTodosLosGastosDeUnGestor(gestorId);
        Double gastoTotal = 0.0;
        for(int i=0; i<gastos.size(); i++ ){
            if(esGastoDelMesEnCurso(gastos.get(i).getFechaVencimiento()))
             gastoTotal += gastos.get(i).getValor();
        }
        return gastoTotal;
    }

    public Integer actualizarCantidadServiciosPorVencerMesEnCurso(Long gestorId){
        List<Gasto> gastos = this.repositorioGestorDeGastos.obtenerTodosLosGastosDeUnGestor(gestorId);
        int cantidad = 0;
        for(int i=0; i<gastos.size(); i++ ){
            if((!esGastoVencido(gastos.get(i).getFechaVencimiento())) && esGastoDelMesEnCurso(gastos.get(i).getFechaVencimiento())){
                cantidad++;
            }
        }
        return cantidad;
    }

    public List<Gasto> obtenerTodosLosGastosDeUnGestor(Long id){
        return repositorioGestorDeGastos.obtenerTodosLosGastosDeUnGestor(id);
    }

    public Boolean esGastoVencido(Date fechaVencimiento) {
        Calendar fechaActualSinHora = Calendar.getInstance();
        fechaActualSinHora.set(Calendar.HOUR_OF_DAY, 0);
        fechaActualSinHora.set(Calendar.MINUTE, 0);
        fechaActualSinHora.set(Calendar.SECOND, 0);
        fechaActualSinHora.set(Calendar.MILLISECOND, 0);

        // Configurar la fecha a comparar sin horas
        Calendar fechaVencimientoSinHora = Calendar.getInstance();
        fechaVencimientoSinHora.setTime(fechaVencimiento);
        fechaVencimientoSinHora.set(Calendar.HOUR_OF_DAY, 0);
        fechaVencimientoSinHora.set(Calendar.MINUTE, 0);
        fechaVencimientoSinHora.set(Calendar.SECOND, 0);
        fechaVencimientoSinHora.set(Calendar.MILLISECOND, 0);

        return fechaVencimientoSinHora.before(fechaActualSinHora.getTime());
    }

    public Boolean esGastoDelMesEnCurso(Date fechaAComparar){
        Date fechaActual = new Date();

        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar.setTime(fechaActual);
        calendar2.setTime(fechaAComparar);

        return (calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH));
    }

}
