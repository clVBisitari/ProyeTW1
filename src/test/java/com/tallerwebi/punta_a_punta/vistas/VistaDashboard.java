package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaDashboard extends VistaWeb {

    public VistaDashboard(Page page) {
        super(page);
        page.navigate("localhost:8080/spring/login");
    }

    public void hacerLogin(){
        this.escribirEmail("test@unlam.edu.ar");
        this.escribirClave("test");
        this.darClickEnIniciarSesion();
    }

    public String obtenerTitulo(){
        return this.obtenerTextoDelElemento("h2.dashboard__usertext");
    }

    public String obtenerSaldo(){
        return this.obtenerTextoDelElemento("#saldoTotal");
    }

    public String obtenerGastos(){
        return this.obtenerTextoDelElemento("#gastosMes");
    }

    public void escribirEmail(String email){
        this.escribirEnElElemento("#email", email);
    }

    public void escribirClave(String clave){
        this.escribirEnElElemento("#password", clave);
    }

    public void darClickEnIniciarSesion(){
        this.darClickEnElElemento("#btn-login");
    }

    public void darClickEnIrAInversiones(){
        this.darClickEnElElemento("#linkInversiones");
    }

}
