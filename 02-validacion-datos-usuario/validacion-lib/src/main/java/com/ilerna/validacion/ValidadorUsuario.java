package com.ilerna.validacion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Librería de validación de datos de usuario.
 * Todos los métodos son estáticos y devuelven un {@link ResultadoValidacion}.
 * Las reglas configurables se cargan desde el fichero
 * {@code reglas-validacion.properties} en el classpath.
 */
public class ValidadorUsuario {

    // ── Constantes ──────────────────────────────────────────────────────────

    private static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

    private static final Pattern PATRON_EMAIL =
            Pattern.compile("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");

    private static final Pattern PATRON_DNI =
            Pattern.compile("^(\\d{8})([A-Za-z])$");

    private static final Pattern PATRON_NIE =
            Pattern.compile("^([XYZxyz])(\\d{7})([A-Za-z])$");

    // ── Configuración cargada desde properties ───────────────────────────────

    private static final int PASSWORD_MIN_LONGITUD;

    static {
        Properties props = new Properties();
        try (InputStream is = ValidadorUsuario.class
                .getClassLoader()
                .getResourceAsStream("reglas-validacion.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            // Usa valor por defecto si no se puede cargar el fichero
        }
        PASSWORD_MIN_LONGITUD = Integer.parseInt(
                props.getProperty("password.min.longitud", "8"));
    }

    // ── Métodos de validación ────────────────────────────────────────────────

    /**
     * Valida un email con expresión regular estándar RFC-compatible.
     */
    public static ResultadoValidacion validarEmail(String email) {
        if (email == null || email.isBlank()) {
            return ResultadoValidacion.error("El email no puede estar vacío.");
        }
        if (!PATRON_EMAIL.matcher(email).matches()) {
            return ResultadoValidacion.error("El email no tiene un formato válido.");
        }
        return ResultadoValidacion.ok();
    }

    /**
     * Valida una contraseña aplicando las reglas de seguridad configuradas:
     * longitud mínima, al menos una mayúscula, un número y un carácter especial.
     */
    public static ResultadoValidacion validarPassword(String password) {
        if (password == null || password.isEmpty()) {
            return ResultadoValidacion.error("La contraseña no puede estar vacía.");
        }
        if (password.length() < PASSWORD_MIN_LONGITUD) {
            return ResultadoValidacion.error(
                    "La contraseña debe tener al menos " + PASSWORD_MIN_LONGITUD + " caracteres.");
        }
        if (password.chars().noneMatch(Character::isUpperCase)) {
            return ResultadoValidacion.error("La contraseña debe contener al menos una letra mayúscula.");
        }
        if (password.chars().noneMatch(Character::isDigit)) {
            return ResultadoValidacion.error("La contraseña debe contener al menos un número.");
        }
        if (password.chars().noneMatch(c -> "!@#$%^&*()_+-=[]{}|;':\",./<>?".indexOf(c) >= 0)) {
            return ResultadoValidacion.error("La contraseña debe contener al menos un carácter especial.");
        }
        return ResultadoValidacion.ok();
    }

    /**
     * Valida un DNI español: 8 dígitos + letra de control.
     */
    public static ResultadoValidacion validarDNI(String dni) {
        if (dni == null || dni.isBlank()) {
            return ResultadoValidacion.error("El DNI no puede estar vacío.");
        }
        var matcher = PATRON_DNI.matcher(dni.trim().toUpperCase());
        if (!matcher.matches()) {
            return ResultadoValidacion.error("El DNI debe tener 8 dígitos seguidos de una letra (ej: 12345678Z).");
        }
        int numero = Integer.parseInt(matcher.group(1));
        char letraProporcionada = matcher.group(2).charAt(0);
        char letraEsperada = LETRAS_DNI.charAt(numero % 23);
        if (letraProporcionada != letraEsperada) {
            return ResultadoValidacion.error(
                    "La letra del DNI no es correcta. Se esperaba: " + letraEsperada);
        }
        return ResultadoValidacion.ok();
    }

    /**
     * Valida un NIE español: letra inicial (X/Y/Z) + 7 dígitos + letra de control.
     */
    public static ResultadoValidacion validarNIE(String nie) {
        if (nie == null || nie.isBlank()) {
            return ResultadoValidacion.error("El NIE no puede estar vacío.");
        }
        var matcher = PATRON_NIE.matcher(nie.trim().toUpperCase());
        if (!matcher.matches()) {
            return ResultadoValidacion.error("El NIE debe tener formato X/Y/Z + 7 dígitos + letra (ej: X1234567L).");
        }
        char inicial = matcher.group(1).charAt(0);
        String digitos = matcher.group(2);
        char letraProporcionada = matcher.group(3).charAt(0);

        int prefijoNumerico = switch (inicial) {
            case 'X' -> 0;
            case 'Y' -> 1;
            case 'Z' -> 2;
            default -> throw new IllegalStateException("Prefijo NIE inesperado: " + inicial);
        };

        int numero = Integer.parseInt(prefijoNumerico + digitos);
        char letraEsperada = LETRAS_DNI.charAt(numero % 23);

        if (letraProporcionada != letraEsperada) {
            return ResultadoValidacion.error(
                    "La letra del NIE no es correcta. Se esperaba: " + letraEsperada);
        }
        return ResultadoValidacion.ok();
    }
}
