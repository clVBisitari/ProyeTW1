package com.tallerwebi.dominio.interfaces;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.presentacion.CardPaymentDTO;
import com.tallerwebi.presentacion.PaymentResponseDTO;

public interface ServicioMercadoPago {
    Preference crearPreferenciaDePago(Double monto) throws MPException, MPApiException;
    public PaymentResponseDTO processPayment(CardPaymentDTO cardPaymentDTO);
}
