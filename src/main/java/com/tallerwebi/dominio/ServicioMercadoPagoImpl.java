package com.tallerwebi.dominio;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.payment.*;
import com.mercadopago.client.preference.*;
import com.mercadopago.core.MPRequestOptions;
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
import java.util.*;
import java.util.logging.Level;

@Service
public class ServicioMercadoPagoImpl implements ServicioMercadoPago {

    @Value("APP_USR-6820296144818196-110423-32be5774b8b8e7be4d67075dfa0ca4b5-3143139")
    private String accessToken;
    private PreferenceClient preferenceClient;
    private PreferenceItemRequest.PreferenceItemRequestBuilder preferenceItemRequest;

    @Autowired
    public ServicioMercadoPagoImpl(PreferenceClient prefClient, PreferenceItemRequest.PreferenceItemRequestBuilder prefItemReq) throws MPException {
        // Inicializa MercadoPago con el Access Token
        //public-key : TEST-371b5a27-0c64-4089-8aea-b04926e2c230
        //access-token: TEST-6820296144818196-110423-e17c0175f97ce1e9f30b8039da94cae7-3143139
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

    public PaymentResponseDTO processPayment(CardPaymentDTO request) {
        try {
            MercadoPagoConfig.setAccessToken(accessToken);
            Map<String, String> customHeaders = new HashMap<>();
            customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());

            MPRequestOptions requestOptions = MPRequestOptions.builder()
                    .customHeaders(customHeaders)
                    .build();

            PaymentClient client = new PaymentClient();

            List<PaymentItemRequest> items = new ArrayList<>();

            PaymentItemRequest item =
                    PaymentItemRequest.builder()
                            .id("MLB2907679857")
                            .title("Point Mini")
                            .description("Point product for card payments via Bluetooth.")
                            .pictureUrl(
                                    "https://http2.mlstatic.com/resources/frontend/statics/growth-sellers-landings/device-mlb-point-i_medium2x.png")
                            .categoryId("electronics")
                            .quantity(1)
                            .unitPrice(new BigDecimal("58.8"))
                            .build();

            items.add(item);

            PaymentCreateRequest createRequest =
                    PaymentCreateRequest.builder()
                            .additionalInfo(
                                    PaymentAdditionalInfoRequest.builder()
                                            .items(items)
                                            .payer(
                                                    PaymentAdditionalInfoPayerRequest.builder()
                                                            .firstName("Test")
                                                            .lastName("Test")
                                                            .phone(
                                                                    PhoneRequest.builder().areaCode("11").number("987654321").build())
                                                            .build())
                                            .shipments(
                                                    PaymentShipmentsRequest.builder()
                                                            .receiverAddress(
                                                                    PaymentReceiverAddressRequest.builder()
                                                                            .zipCode("12312-123")
                                                                            .stateName("Rio de Janeiro")
                                                                            .cityName("Buzios")
                                                                            .streetName("Av das Nacoes Unidas")
                                                                            .streetNumber("3003")
                                                                            .build())
                                                            .build())
                                            .build())
                            .binaryMode(false)
                            .capture(false)
                            .description("Payment for product")
                            .externalReference("MP0001")
                            .installments(1)
//                            .order(PaymentOrderRequest.builder().type("mercadolibre").id(1234L).build())
                            .payer(PaymentPayerRequest.builder()
                                    .entityType("individual")
                                    .type("customer")
                                    .email("test_user_123@testuser.com")
                                    .identification(IdentificationRequest.builder()
                                            .type("CPF")
                                            .number("01234567890")
                                            .build())
                                    .build())
                            .paymentMethodId("master")
                            .token("ff8080814c11e237014c1ff593b57b4d")
                            .transactionAmount(new BigDecimal("58.8"))
                            .build();
            var createdPayment = client.create(createRequest, requestOptions);

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


