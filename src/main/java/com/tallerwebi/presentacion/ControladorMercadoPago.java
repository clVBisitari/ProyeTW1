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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.exceptions.MPException;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
public class ControladorMercadoPago {

    private final ServicioMercadoPago mercadoPagoService;
    @Autowired
    public ControladorMercadoPago(ServicioMercadoPago mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @PostMapping("/crear-pago")
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

    @Transactional
    @RequestMapping(path = "/process_payment", method = RequestMethod.POST)
    public ModelAndView processPayment(@RequestBody CardPaymentDTO cardPaymentDTO) throws PagoException {
        PaymentResponseDTO payment = mercadoPagoService.processPayment(cardPaymentDTO);
        ModelMap model = new ModelMap();
        model.addAttribute("response", ResponseEntity.status(HttpStatus.CREATED).body(payment));
        return new ModelAndView("redirect:/pago", model);
    }
}

