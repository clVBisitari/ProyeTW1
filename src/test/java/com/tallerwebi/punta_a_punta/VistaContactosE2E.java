package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.tallerwebi.punta_a_punta.vistas.VistaContactos;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class VistaContactosE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaContactos vistaContactos;

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
        vistaContactos = new VistaContactos(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void sinEstarLogueadoDeberiaRedirigir() {
        vistaContactos.navegar("http://localhost:8080/spring/contactos");

        String url = vistaContactos.obtenerURLActual();

        assertThat(url, containsStringIgnoringCase("/spring/login"));
    }

    @Test
    void despuesDelLoginYClickEnDashboardAContactosMostrarPagina() {

        vistaContactos.hacerLogin();

        vistaContactos.darClickEnIrAContactos();

        String url = vistaContactos.obtenerURLActual();

        assertThat(url, containsStringIgnoringCase("/spring/contactos"));
    }

    @Test
    void contactosSugeridosDebeIncluirADiego() {

        vistaContactos.hacerLogin();

        vistaContactos.darClickEnIrAContactos();

        List<String> sugeridos = vistaContactos.obtenerContactosSugeridos();

        assertThat(sugeridos, hasItem("Diego"));

    }

    @Test
    void agregarContactoDebeAparecerEnContactos() throws InterruptedException {

        vistaContactos.hacerLogin();

        vistaContactos.darClickEnIrAContactos();

        int contactosAntes = vistaContactos.obtenerContactos().size();

        vistaContactos.hacerClickEnElPrimerAgregarContacto();

        List<String> contactos = vistaContactos.obtenerContactos();

        assertThat(contactos.size(), is(contactosAntes));

    }

}
