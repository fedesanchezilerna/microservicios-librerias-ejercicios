package com.ilerna._1gestionpagos.service.pasarela;

import com.ilerna._1gestionpagos.model.Pago;
import org.springframework.stereotype.Component;

/**
 * Implementación Strategy para pagos con tarjeta de crédito/débito.
 * Simula el procesamiento con una pasarela de tarjetas.
 */
@Component
public class PagoTarjeta implements PasarelaPago {

    @Override
    public void procesar(Pago pago) {
        // Simulación: el pago con tarjeta siempre se completa
        System.out.printf("[PasoTarjeta] Procesando pago de %.2f %s con tarjeta...%n",
                pago.getMonto(), pago.getMoneda());
        pago.setEstado(Pago.EstadoPago.COMPLETADO);
        System.out.println("[PagoTarjeta] Pago completado correctamente.");
    }
}
