package com.ilerna.validacion;

/**
 * Resultado de una operación de validación.
 * Encapsula si el valor es válido y, en caso negativo, el mensaje de error.
 */
public class ResultadoValidacion {

    private final boolean valido;
    private final String mensajeError;

    private ResultadoValidacion(boolean valido, String mensajeError) {
        this.valido = valido;
        this.mensajeError = mensajeError;
    }

    public static ResultadoValidacion ok() {
        return new ResultadoValidacion(true, null);
    }

    public static ResultadoValidacion error(String mensaje) {
        return new ResultadoValidacion(false, mensaje);
    }

    public boolean isValido() {
        return valido;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    @Override
    public String toString() {
        return valido ? "VÁLIDO" : "INVÁLIDO: " + mensajeError;
    }
}
