package com.tallerwebi.dominio.excepcion;

public class ContactoExistente extends RuntimeException {
  public ContactoExistente(String message) {
    super(message);
  }
}
