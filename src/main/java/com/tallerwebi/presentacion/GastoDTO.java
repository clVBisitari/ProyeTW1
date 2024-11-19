package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Frecuencia;
import com.tallerwebi.dominio.Gasto;
import org.apache.commons.beanutils.converters.CalendarConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class GastoDTO {

    private Long id;
    private String descripcion;
    private String valor;
    private String fechaVencimiento;
    private String fechaRecordatorio;
    private Frecuencia frecuencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getValor() {

        return new BigDecimal(valor.replace(",", "."));
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor.toString();
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaRecordatorio() {
        return fechaRecordatorio;
    }

    public void setFechaRecordatorio(String fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GastoDTO gastoDTO = (GastoDTO) o;
        return valor.compareTo(gastoDTO.valor) == 0 && Objects.equals(id, gastoDTO.id) && Objects.equals(descripcion, gastoDTO.descripcion) && Objects.equals(fechaVencimiento, gastoDTO.fechaVencimiento) && Objects.equals(fechaRecordatorio, gastoDTO.fechaRecordatorio) && frecuencia == gastoDTO.frecuencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion, valor, fechaVencimiento, fechaRecordatorio, frecuencia);
    }

    @Override
    public String toString() {
        return "GastoDTO{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", valor=" + valor +
                ", fechaVencimiento=" + fechaVencimiento +
                ", fechaRecordatorio=" + fechaRecordatorio +
                ", frecuencia=" + frecuencia +
                '}';
    }

    public static GastoDTO convertirGastoaDTO(Gasto gasto){
        GastoDTO gastoDTO = new GastoDTO();

        gastoDTO.setId(gasto.getId());
        gastoDTO.setFrecuencia(gasto.getFrecuencia());
        gastoDTO.setValor(gasto.getValor());
        gastoDTO.setDescripcion(gasto.getDescripcion());

        if (gasto.getFechaVencimiento() != null) {
            gastoDTO.setFechaVencimiento(gasto.getFechaVencimiento().toString());
        }

        if (gasto.getFechaRecordatorio() != null) {
            gastoDTO.setFechaRecordatorio(gasto.getFechaRecordatorio().toString());
        }

        return gastoDTO;
    }

    public static Gasto convertirDTOaGasto(GastoDTO gastoDTO){
        Gasto gasto = new Gasto();

        gasto.setId(gastoDTO.getId());
        gasto.setFrecuencia(gastoDTO.getFrecuencia());
        gasto.setValor(gastoDTO.getValor());
        gasto.setDescripcion(gastoDTO.getDescripcion());
        LocalDate fechaRecordatorio = convertirStringALocalDate(gastoDTO.getFechaRecordatorio());
        Date fechaRecordatorioDate = Date.from(fechaRecordatorio.atStartOfDay(ZoneId.systemDefault()).toInstant());

        gasto.setFechaRecordatorio(fechaRecordatorioDate);

        Date fechaVencimiento = Date.from(convertirStringALocalDate(gastoDTO.getFechaVencimiento()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        gasto.setFechaVencimiento(fechaVencimiento);

        return gasto;
    }

    public static LocalDate convertirStringALocalDate(String fecha){

        if (fecha.isEmpty()) return null;

        DateTimeFormatter formatoFechas = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(fecha, formatoFechas);
    }

}
