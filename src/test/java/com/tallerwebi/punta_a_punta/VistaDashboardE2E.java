package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.tallerwebi.punta_a_punta.vistas.VistaDashboard;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class VistaDashboardE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaDashboard vistaDashboard;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();
        vistaDashboard = new VistaDashboard(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void sinEstarLogueadoDeberiaRedirigir() {
        String url = vistaDashboard.obtenerURLActual();

        vistaDashboard.navegar("http://localhost:8080/spring/dashboard");

        assertThat(url, containsStringIgnoringCase("/spring/login"));
    }

    @Test
    void deberiaDecirBienvenidoEnTituloConElNombre() {

        vistaDashboard.hacerLogin();

        String texto = vistaDashboard.obtenerTitulo();

        assertThat("Bienvenido, " + "Juan" +"!", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaDarElSaldoTotal() {

        vistaDashboard.hacerLogin();

        String texto = vistaDashboard.obtenerSaldo();

        assertThat("10500.00", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaDarLosGastosDelMes() {

        vistaDashboard.hacerLogin();

        String texto = vistaDashboard.obtenerGastos();

        assertThat("237000.65", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaNavegarAInversiones() {

        vistaDashboard.hacerLogin();

        vistaDashboard.darClickEnIrAInversiones();

        String url = vistaDashboard.obtenerURLActual();

        assertThat(url, containsStringIgnoringCase("/spring/inversiones"));
    }

}
