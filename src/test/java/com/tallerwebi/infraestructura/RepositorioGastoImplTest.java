package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Frecuencia;
import com.tallerwebi.dominio.Gasto;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class RepositorioGastoImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Mock
    private Usuario usuarioMock;

    private RepositorioGastoImpl repositorioGastoImpl;
    private DateFormat formatoFechas = new SimpleDateFormat("yyyy-MM-dd");


    @BeforeEach
    public void init(){

        this.repositorioGastoImpl = new RepositorioGastoImpl(sessionFactory);
        this.usuarioMock = new Usuario();
        this.usuarioMock.setId(12345678);
        this.usuarioMock.setNombre("Usuario");
        this.usuarioMock.setApellido("UnoDosTresCuatro");
        this.usuarioMock.setEmail("usuario@email.com");
        this.sessionFactory.getCurrentSession().save(this.usuarioMock);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnGasto() throws ParseException {
        var gasto = dadoQueExisteUnGasto();

        Boolean gastoGuardado = this.repositorioGastoImpl.guardarGasto(this.usuarioMock.getId(), gasto);

        assertThat(gastoGuardado, equalTo(true));
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedaModificarElValorDeUnGastoExistente() throws ParseException {
//        dadoQueExisteUnGasto();
//
//        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();
//        Gasto gastoAModificar = gastos.get(0);
//        gastoAModificar.setValor(new BigDecimal(7000));
//        this.repositorioGastoImpl.modificarValorDeUnGasto(gastoAModificar.getId(), gastoAModificar.getValor());
//        Gasto gastoModificado = this.repositorioGastoImpl.buscarGastoPorId(gastoAModificar.getId());
//        BigDecimal valorEsperado = gastoModificado.getValor();
//
//        assertThat(valorEsperado, equalTo(new BigDecimal(7000)));
//    }

//    @Test
//    @Transactional
//    @Rollback
//    public void queSePuedaEliminarUnGastoExistente() throws ParseException {
//        dadoQueExisteUnGasto();
//
//        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();
//        Gasto gastoAEliminar = gastos.get(0);
//        this.repositorioGastoImpl.eliminarGasto(gastoAEliminar.getId());
//        gastos.removeIf(Gasto -> Gasto.getId() == gastoAEliminar.getId());
//        gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();
//
//        assertThat(gastos.size(), equalTo(0));
//    }

//    @Test
//    @Transactional
//    @Rollback
//    public void QueSePuedaBuscarUnGastoPorDescripcion() {
//        dadoQueExisteUnGasto();
//
//        List<Gasto> gastos = this.repositorioGastoImpl.buscarGastoPorDescripcion("impuesto");
//
//        assertThat(gastos.size(), equalTo(1));
//    }

//    @Test
//    @Transactional
//    @Rollback
//    public void QueSePuedaModificarLaFechaDeVencimientoDeUnGasto() {
//        dadoQueExisteUnGasto();
//
//        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();
//
//        Gasto gastoAModificar = gastos.get(0);
//
//        String fechaString = "2024-12-10";
//
//        DateTimeFormatter formatoFechas = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        Date nuevaFechaVencimiento = new Date(fechaString);
//
//        gastoAModificar.setFechaVencimiento(nuevaFechaVencimiento);
//
//        this.repositorioGastoImpl.modificarFechaDeVencimientoDeUnGasto(gastoAModificar.getId(), gastoAModificar.getFechaVencimiento());
//
//        Gasto gastoModificado = this.repositorioGastoImpl.buscarGastoPorId(gastoAModificar.getId());
//
//        Date fechaObtenida = gastoModificado.getFechaVencimiento();
//
//        assertThat(fechaObtenida, equalTo(nuevaFechaVencimiento));
//    }

//    @Test
//    @Transactional
//    @Rollback
//    public void QueSePuedaModificarLaFechaDeRecordatorioDeUnGasto() {
//
//        dadoQueExisteUnGasto();
//
//        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();
//
//        Gasto gastoAModificar = gastos.get(0);
//
//        String fechaString = "2024-12-08";
//
//        DateTimeFormatter formatoFechas = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        Date nuevaFechaRecordatorio = new Date(fechaString);
//
//        gastoAModificar.setFechaRecordatorio(nuevaFechaRecordatorio);
//
//        this.repositorioGastoImpl.modificarFechaDeRecordatorioDeUnGasto(gastoAModificar.getId(), gastoAModificar.getFechaRecordatorio());
//
//        Gasto gastoModificado = this.repositorioGastoImpl.buscarGastoPorId(gastoAModificar.getId());
//
//        Date fechaObtenida = gastoModificado.getFechaRecordatorio();
//
//        assertThat(fechaObtenida, equalTo(nuevaFechaRecordatorio));
//    }

//    @Test
//    @Transactional
//    @Rollback
//    public void QueSePuedaModificarLaFrecuenciaDeUnGasto() {
//
//        dadoQueExisteUnGasto();
//
//        List<Gasto> gastos = this.repositorioGastoImpl.obtenerTodosLosGastos();
//
//        Gasto gastoAModificar = gastos.get(0);
//
//        gastoAModificar.setFrecuencia(Frecuencia.SEMESTRAL);
//
//        this.repositorioGastoImpl.modificarFrecuenciaDeUnGasto(gastoAModificar.getId(), gastoAModificar.getFrecuencia());
//
//        Gasto gastoModificado = this.repositorioGastoImpl.buscarGastoPorId(gastoAModificar.getId());
//
//        Frecuencia frecuenciaEsperada = gastoModificado.getFrecuencia();
//
//        assertThat(frecuenciaEsperada, equalTo(Frecuencia.SEMESTRAL));
//
//    }


    private Gasto dadoQueExisteUnGasto() {

        String fechaString = "2024-10-10";

        DateTimeFormatter formatoFechas = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Date fechaVencimiento = Date.from(LocalDate.parse(fechaString).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Gasto gasto = new Gasto("Impuesto", new BigDecimal(50000), fechaVencimiento, Frecuencia.MENSUAL);

        return gasto;
    }

}

