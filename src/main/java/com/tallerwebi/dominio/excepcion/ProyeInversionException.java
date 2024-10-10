package com.tallerwebi.dominio.excepcion;

public class ProyeInversionException extends RuntimeException {

    // Constructor por defecto
    public ProyeInversionException() {
        super();
    }

    // Constructor con mensaje personalizado
    public ProyeInversionException(String message) {
        super(message);
    }

    // Constructor con mensaje y causa
    public ProyeInversionException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor con causa
    public ProyeInversionException(Throwable cause) {
        super(cause);
    }
}
