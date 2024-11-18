package com.tallerwebi.presentacion;
import com.mercadopago.exceptions.MPApiException;
import com.tallerwebi.dominio.ServicioMercadoPagoImpl;
import com.tallerwebi.dominio.excepcion.PagoException;
import com.tallerwebi.dominio.interfaces.ServicioMercadoPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.exceptions.MPException;

@Controller
public class ControladorMercadoPago {

    private final ServicioMercadoPago mercadoPagoService;
    @Autowired
    public ControladorMercadoPago(ServicioMercadoPago mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @GetMapping("/crear-pago")
    public String crearPago(@RequestParam("monto") Double monto, Model model) throws PagoException {
        try {
            // Crear la preferencia de pago
            Preference preference = mercadoPagoService.crearPreferenciaDePago(monto);

            // Pasar el enlace de MercadoPago al modelo Thymeleaf
            model.addAttribute("initPoint", preference.getInitPoint());

            return "redirect:/pago";
        } catch (MPException e) {
            e.printStackTrace();
            return "error";
        } catch (MPApiException e) {
            throw new PagoException(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody CardPaymentDTO cardPaymentDTO) {
        PaymentResponseDTO payment = mercadoPagoService.processPayment(cardPaymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

}

