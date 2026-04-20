package com.ilerna._1gestionpagos.service.pasarela;

import com.ilerna._1gestionpagos.model.Pago;
import org.springframework.stereotype.Component;

/**
 * Implementación Strategy para pagos con PayPal.
 * Simula el procesamiento con la API de PayPal.
 */
@Component
public class PagoPayPal implements PasarelaPago {

    @Override
    public void procesar(Pago pago) {
        // Simulación: el pago con PayPal se completa si el monto es positivo
        System.out.printf("[PagoPayPal] Procesando pago de %.2f %s con PayPal...%n",
                pago.getMonto(), pago.getMoneda());

        if (pago.getMonto().signum() > 0) {
            pago.setEstado(Pago.EstadoPago.COMPLETADO);
            System.out.println("[PagoPayPal] Pago completado correctamente.");
        } else {
            pago.setEstado(Pago.EstadoPago.FALLIDO);
            System.out.println("[PagoPayPal] Pago fallido: monto no válido.");
        }
    }
}
