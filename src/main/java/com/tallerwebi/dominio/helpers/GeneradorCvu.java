package com.tallerwebi.dominio.helpers;

import java.util.Random;

public class GeneradorCvu {

    public static String generarCvu() {
        // Generar CVU ficticio
        String bloque1 = generarBloque(7); // 7 dígitos aleatorios para el primer bloque
        int verificador1 = calcularDigitoVerificador(bloque1);

        String bloque2 = generarBloque(13); // 13 dígitos aleatorios para el segundo bloque
        int verificador2 = calcularDigitoVerificador(bloque2);

        // Combinar bloques con verificadores
        String cvu = bloque1 + verificador1 + bloque2 + verificador2;

        System.out.println("CVU Ficticio: " + cvu);
        return cvu;
    }

    // Genera un bloque de números aleatorios de longitud especificada
    public static String generarBloque(int longitud) {
        Random random = new Random();
        StringBuilder bloque = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            bloque.append(random.nextInt(10)); // Dígitos entre 0 y 9
        }
        return bloque.toString();
    }

    // Calcula el dígito verificador usando el algoritmo módulo 10
    public static int calcularDigitoVerificador(String bloque) {
        int suma = 0;
        boolean alternar = true;

        // Iterar desde el final hacia el principio
        for (int i = bloque.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(bloque.charAt(i));

            if (alternar) {
                digito *= 2;
                if (digito > 9) {
                    digito -= 9; // Si el resultado es mayor que 9, restar 9
                }
            }

            suma += digito;
            alternar = !alternar;
        }

        return (10 - (suma % 10)) % 10;
    }
}

