package com.tallerwebi.dominio.excepcion;

public class PagoException extends Exception{

    public PagoException() {
        super();
    }

    public PagoException(String message) {
        super(message);
    }

    public PagoException(String message, Throwable cause) {
        super(message, cause);
    }

    public PagoException(Throwable cause) {
        super(cause);
    }
}
