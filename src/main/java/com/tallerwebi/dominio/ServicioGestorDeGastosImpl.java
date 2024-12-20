package com.tallerwebi.dominio;

import com.tallerwebi.dominio.helpers.FechaHelper;
import com.tallerwebi.infraestructura.RepositorioGastoImpl;
import com.tallerwebi.dominio.interfaces.ServicioGestorGastos;
import com.tallerwebi.infraestructura.RepositorioGestorDeGastosImpl;
import com.tallerwebi.presentacion.GastoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressWarnings("LanguageDetectionInspection")
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

    public Boolean guardarGasto(Integer userId, GastoDTO gastoDto){
        var gasto = GastoDTO.convertirDTOaGasto(gastoDto);
        return repositorioGasto.guardarGasto(userId, gasto);
    }

    @Override
    public BigDecimal obtenerGastosDelMes(Integer usuarioId) {
        return repositorioGestorDeGastos.obtenerGastosDelMes(usuarioId);
    }

    public BigDecimal actualizarTotalGastosDelMesEnCursoPorId(Integer userId) {
        List<Gasto> gastos = this.repositorioGasto.obtenerTodosLosGastosPorUsuarioId(userId);

        BigDecimal gastoTotal = new BigDecimal(0.0);
        for (Gasto gasto : gastos) {

            if (esGastoDelMesEnCurso(gasto.getFechaVencimiento())) {
                gastoTotal  = gastoTotal.add(gasto.getValor());
            }
        }

        return gastoTotal;
    }

    public Integer actualizarCantidadServiciosPorVencerMesEnCurso(Integer usuarioId){
        List<Gasto> gastos = this.repositorioGasto.obtenerTodosLosGastosPorUsuarioId(usuarioId);
        int cantidad = 0;

        for (Gasto gasto : gastos) {
            if ((!esGastoVencido(gasto.getFechaVencimiento())) && esGastoDelMesEnCurso(gasto.getFechaVencimiento())) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public List<Gasto> obtenerTodosLosGastosDeUnGestor(Integer usuarioId){
        return repositorioGestorDeGastos.obtenerTodosLosGastosDeUnGestor(usuarioId);
    }

    @Override
    public List<Gasto> obtenerLosGastosDelMesEnCursoDeUnUsuario(Integer id){
        List<Gasto> gastos = this.repositorioGasto.obtenerTodosLosGastosPorUsuarioId(id);
        List<Gasto> gastoDelMesEnCurso = new ArrayList<Gasto>();

        for (Gasto gasto : gastos) {
            if (esGastoDelMesEnCurso(gasto.getFechaVencimiento())) {
                gastoDelMesEnCurso.add(gasto);
            }
        }
        return gastoDelMesEnCurso;
    }

    @Override
    public List<Gasto> obtenerLosGastosNoVencidosDeUnUsuario(Integer id){
            List<Gasto> gastos = this.repositorioGasto.obtenerTodosLosGastosPorUsuarioId(id);
            List<Gasto> gastoNoVencidos = new ArrayList<Gasto>();

            for (Gasto gasto : gastos) {
                if (!esGastoVencido(gasto.getFechaVencimiento())) {
                    gastoNoVencidos.add(gasto);
                }
        }
        return gastoNoVencidos;
    }

    @Override
    public List<Gasto> buscarGastosPorDescripcion(Integer usuarioId, String descripcion){
        List<Gasto> gastosEncontrados = repositorioGasto.buscarGastosDeUnUsuarioPorDescripcion(usuarioId, descripcion);
        return gastosEncontrados;
    }

    public void eliminarGasto(Long id){
        this.repositorioGasto.eliminarGasto(id);
    }

    public Boolean esGastoVencido(Date fechaVencimiento) {
        Calendar fechaActualSinHora = Calendar.getInstance();
        fechaActualSinHora.set(Calendar.HOUR_OF_DAY, 0);
        fechaActualSinHora.set(Calendar.MINUTE, 0);
        fechaActualSinHora.set(Calendar.SECOND, 0);
        fechaActualSinHora.set(Calendar.MILLISECOND, 0);
        LocalDate fechaActual = LocalDate.now();

        // Configurar la fecha a comparar sin horas
//        Calendar fechaVencimientoSinHora = Calendar.getInstance();
//        fechaVencimientoSinHora.setTime(fechaVencimiento);
//        fechaVencimientoSinHora.set(Calendar.HOUR_OF_DAY, 0);
//        fechaVencimientoSinHora.set(Calendar.MINUTE, 0);
//        fechaVencimientoSinHora.set(Calendar.SECOND, 0);
//        fechaVencimientoSinHora.set(Calendar.MILLISECOND, 0);
        LocalDate fechaVenc = FechaHelper.convertToLocalDate(fechaVencimiento);
        return fechaVenc.isBefore(fechaActual);
    }

    public Boolean esGastoDelMesEnCurso(Date fechaAComparar){
        Date fechaActual = new Date();
        LocalDate fechaLocalActual = LocalDate.now();

//        Calendar calendar = Calendar.getInstance();
//        Calendar calendar2 = Calendar.getInstance();
//
//        calendar.setTime(fechaActual);
//        calendar2.setTime(fechaAComparar);
        return fechaLocalActual.getMonth() == fechaAComparar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth();
//        return (calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH));
    }

}
