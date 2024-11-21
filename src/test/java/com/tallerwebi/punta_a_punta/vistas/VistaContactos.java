package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

import java.util.List;

public class VistaContactos extends VistaWeb {

    public VistaContactos(Page page) {
        super(page);
        page.navigate("localhost:8080/spring/login");
    }

    public void hacerLogin(){
        this.escribirEmail("test@unlam.edu.ar");
        this.escribirClave("test");
        this.darClickEnIniciarSesion();
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

    public void darClickEnIrAContactos(){
        this.darClickEnElElemento("#linkContactos");
    }

    public List<String> obtenerContactosSugeridos(){
        return this.obtenerElementos("[data-testid='sugest-name']").allTextContents();
    }

    public List<String> obtenerContactos(){
        return this.obtenerElementos("[data-testid='contacto-name']").allTextContents();
    }

    public void hacerClickEnElPrimerAgregarContacto(){
        this.darClickEnPrimerElemento("[data-testid='sugest-add']");
    }

}
