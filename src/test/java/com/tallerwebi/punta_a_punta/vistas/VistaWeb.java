package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class VistaWeb {
    protected Page page;

    public VistaWeb(Page page) {
        this.page = page;
    }

    public String obtenerURLActual(){
        return page.url();
    }

    public void navegar(String url) {
        page.navigate(url);
    }

    protected String obtenerTextoDelElemento(String selectorCSS){
        return this.obtenerElemento(selectorCSS).textContent();
    }

    protected void darClickEnElElemento(String selectorCSS){
        this.obtenerElemento(selectorCSS).click();
    }

    protected void darClickEnPrimerElemento(String selectorCSS){
        this.obtenerElemento(selectorCSS).nth(0).click();
    }

    protected void escribirEnElElemento(String selectorCSS, String texto){
        this.obtenerElemento(selectorCSS).type(texto);
    }

    private Locator obtenerElemento(String selectorCSS){
        return page.locator(selectorCSS);
    }

    public Locator obtenerElementos(String selectorCSS) {
        return page.locator(selectorCSS);
    }
}
