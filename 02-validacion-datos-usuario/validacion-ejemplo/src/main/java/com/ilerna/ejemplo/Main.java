package com.ilerna.ejemplo;

import com.ilerna.validacion.ResultadoValidacion;
import com.ilerna.validacion.ValidadorUsuario;

/**
 * Aplicación de consola que demuestra el uso de la librería validacion-lib.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Validación de Datos de Usuario\n");

        // Emails
        System.out.println("--- Email ---");
        probar("Email válido",       ValidadorUsuario.validarEmail("usuario@ejemplo.com"));
        probar("Sin @",              ValidadorUsuario.validarEmail("usuarioejemplo.com"));
        probar("Sin dominio",        ValidadorUsuario.validarEmail("usuario@"));
        probar("Vacío",              ValidadorUsuario.validarEmail(""));

        // Contraseñas
        System.out.println("\n--- Contraseña ---");
        probar("Contraseña válida",       ValidadorUsuario.validarPassword("Segura@123"));
        probar("Demasiado corta",         ValidadorUsuario.validarPassword("Ab1!"));
        probar("Sin mayúscula",           ValidadorUsuario.validarPassword("segura@123"));
        probar("Sin número",              ValidadorUsuario.validarPassword("Segura@abc"));
        probar("Sin carácter especial",   ValidadorUsuario.validarPassword("Segura123"));

        // DNI
        System.out.println("\n--- DNI ---");
        probar("DNI válido (12345678Z)",  ValidadorUsuario.validarDNI("12345678Z"));
        probar("Letra incorrecta",        ValidadorUsuario.validarDNI("12345678A"));
        probar("Formato incorrecto",      ValidadorUsuario.validarDNI("1234567"));
        probar("Vacío",                   ValidadorUsuario.validarDNI(""));

        // NIE
        System.out.println("\n--- NIE ---");
        probar("NIE válido (X1234567L)",  ValidadorUsuario.validarNIE("X1234567L"));
        probar("Letra incorrecta",        ValidadorUsuario.validarNIE("X1234567A"));
        probar("Prefijo incorrecto",      ValidadorUsuario.validarNIE("A1234567L"));
        probar("Vacío",                   ValidadorUsuario.validarNIE(""));
    }

    private static void probar(String descripcion, ResultadoValidacion resultado) {
        System.out.printf("  %-30s → %s%n", descripcion, resultado);
    }
}
