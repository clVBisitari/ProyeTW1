package com.tallerwebi.dominio;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.excepcion.MercadoPagoException;
import com.tallerwebi.dominio.interfaces.ServicioMercadoPago;
import com.tallerwebi.presentacion.CardPaymentDTO;
import com.tallerwebi.presentacion.PaymentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioMercadoPagoImpl implements ServicioMercadoPago {

    @Value("${mercadopago.access.token}")
    private String accessToken;
    private PreferenceClient preferenceClient;
    private PreferenceItemRequest.PreferenceItemRequestBuilder preferenceItemRequest;

    @Autowired
    public ServicioMercadoPagoImpl(PreferenceClient prefClient, PreferenceItemRequest.PreferenceItemRequestBuilder prefItemReq) throws MPException {
        // Inicializa MercadoPago con el Access Token
        com.mercadopago.MercadoPagoConfig.setAccessToken(accessToken);
        preferenceClient = prefClient;
        preferenceItemRequest = prefItemReq;
    }

    public Preference crearPreferenciaDePago(Double monto) throws MPException, MPApiException {
        // Configura el ítem o servicio
        PreferenceItemRequest itemRequest = this.preferenceItemRequest
                .id("1234")
                .title("Depósito de saldo en aplicación")
                .quantity(1)
                .unitPrice(new BigDecimal(monto))
                .build();

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        PreferenceFreeMethodRequest freeMethod =
                PreferenceFreeMethodRequest.builder()
                        .id(1L).build();
        List<PreferenceFreeMethodRequest> freeMethodList = new ArrayList<>();
        freeMethodList.add(freeMethod);

        List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());

        List<PreferencePaymentMethodRequest> excludedPaymentMethods = new ArrayList<>();
        excludedPaymentMethods.add(PreferencePaymentMethodRequest.builder().id("").build());

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .backUrls(
                        PreferenceBackUrlsRequest.builder()
                                .success("http://localhost:8080/success")
                                .failure("http://localhost:8080/failure")
                                .pending("http://localhost:8080/pending")
                                .build())
                .differentialPricing(
                        PreferenceDifferentialPricingRequest.builder()
                                .id(1L)
                                .build())
                .expires(false)
                .items(items)
                .marketplaceFee(new BigDecimal("0"))
                .payer(
                        PreferencePayerRequest.builder()
                                .name("Test")
                                .surname("User")
                                .email("your_test_email@example.com")
                                .phone(PhoneRequest.builder().areaCode("11").number("4444-4444").build())
                                .identification(
                                        IdentificationRequest.builder().type("CPF").number("19119119100").build())
                                .address(
                                        AddressRequest.builder()
                                                .zipCode("06233200")
                                                .streetName("Street")
                                                .streetNumber("123")
                                                .build())
                                .build())
                .additionalInfo("Discount: 12.00")
                .autoReturn("all")
                .binaryMode(true)
                .externalReference("1643827245")
                .marketplace("marketplace")
                .notificationUrl("https://notificationurl.com")
                .operationType("regular_payment")
                .paymentMethods(
                        PreferencePaymentMethodsRequest.builder()
                                .defaultPaymentMethodId("master")
                                .excludedPaymentTypes(excludedPaymentTypes)
                                .excludedPaymentMethods(excludedPaymentMethods)
                                .installments(5)
                                .defaultInstallments(1)
                                .build())
                .shipments(
                        PreferenceShipmentsRequest.builder()
                                .mode("custom")
                                .localPickup(false)
                                .defaultShippingMethod(null)
                                .freeMethods(freeMethodList)
                                .cost(BigDecimal.TEN)
                                .freeShipping(false)
                                .dimensions("10x10x20,500")
                                .receiverAddress(
                                        PreferenceReceiverAddressRequest.builder()
                                                .zipCode("06000000")
                                                .streetNumber("123")
                                                .streetName("Street")
                                                .floor("12")
                                                .apartment("120A")
                                                .build())
                                .build())
                .statementDescriptor("Test Store")
                .build();

        return preferenceClient.create(preferenceRequest);
    }

    public PaymentResponseDTO processPayment(CardPaymentDTO cardPaymentDTO) {
        try {
            MercadoPagoConfig.setAccessToken(accessToken);

            PaymentClient paymentClient = new PaymentClient();

            PaymentCreateRequest paymentCreateRequest =
                    PaymentCreateRequest.builder()
                            .transactionAmount(cardPaymentDTO.getTransactionAmount())
                            .token(cardPaymentDTO.getToken())
                            .installments(cardPaymentDTO.getInstallments())
                            .paymentMethodId(cardPaymentDTO.getPaymentMethodId())
                            .payer(
                                    PaymentPayerRequest.builder()
                                            .email(cardPaymentDTO.getPayer().getEmail())
                                            .identification(
                                                    IdentificationRequest.builder()
                                                            .type(cardPaymentDTO.getPayer().getIdentification().getType())
                                                            .number(cardPaymentDTO.getPayer().getIdentification().getNumber())
                                                            .build())
                                            .build())
                            .build();

            Payment createdPayment = paymentClient.create(paymentCreateRequest);

            return new PaymentResponseDTO(
                    createdPayment.getId(),
                    String.valueOf(createdPayment.getStatus()),
                    createdPayment.getStatusDetail());
        } catch (MPApiException apiException) {
            System.out.println(apiException.getApiResponse().getContent());
            throw new MercadoPagoException(apiException.getApiResponse().getContent());
        } catch (MPException exception) {
            System.out.println(exception.getMessage());
            throw new MercadoPagoException(exception.getMessage());
        }
    }
}


