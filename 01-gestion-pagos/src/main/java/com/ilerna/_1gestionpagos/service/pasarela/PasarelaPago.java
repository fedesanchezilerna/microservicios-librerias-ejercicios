package com.ilerna._1gestionpagos.service.pasarela;

import com.ilerna._1gestionpagos.model.Pago;

/**
 * Interfaz Strategy para las distintas pasarelas de pago.
 * Cada implementación simula el procesamiento con una pasarela diferente.
 */
public interface PasarelaPago {

    /**
     * Procesa el pago y actualiza su estado (COMPLETADO o FALLIDO).
     *
     * @param pago el pago a procesar
     */
    void procesar(Pago pago);
}
