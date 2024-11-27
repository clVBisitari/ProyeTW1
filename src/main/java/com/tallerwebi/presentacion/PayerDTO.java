package com.tallerwebi.presentacion;

import org.jetbrains.annotations.NotNull;

public class PayerDTO {
    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private PayerIdentificationDTO identification;

    public PayerDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PayerIdentificationDTO getIdentification() {
        return identification;
    }

    public void setIdentification(PayerIdentificationDTO identification) {
        this.identification = identification;
    }

    @NotNull
    public String getName() {
        return name;
    }
}
