package com.ilerna._1gestionpagos.service;

import com.ilerna._1gestionpagos.model.Pago;
import com.ilerna._1gestionpagos.repository.PagoRepository;
import com.ilerna._1gestionpagos.service.pasarela.PagoPayPal;
import com.ilerna._1gestionpagos.service.pasarela.PagoTarjeta;
import com.ilerna._1gestionpagos.service.pasarela.PasarelaPago;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final PagoTarjeta pagoTarjeta;
    private final PagoPayPal pagoPayPal;

    public PagoService(PagoRepository pagoRepository,
                       PagoTarjeta pagoTarjeta,
                       PagoPayPal pagoPayPal) {
        this.pagoRepository = pagoRepository;
        this.pagoTarjeta = pagoTarjeta;
        this.pagoPayPal = pagoPayPal;
    }

    /**
     * Procesa un nuevo pago usando la pasarela correspondiente al método indicado.
     *
     * @param pago datos del pago a procesar
     * @return el pago guardado con estado actualizado
     */
    public Pago procesarPago(Pago pago) {
        PasarelaPago pasarela = seleccionarPasarela(pago.getMetodo());
        pasarela.procesar(pago);
        return pagoRepository.save(pago);
    }

    /**
     * Busca un pago por su identificador.
     *
     * @param id identificador del pago
     * @return el pago si existe
     */
    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    /**
     * Devuelve todos los pagos registrados.
     *
     * @return lista de pagos
     */
    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    private PasarelaPago seleccionarPasarela(Pago.MetodoPago metodo) {
        return switch (metodo) {
            case TARJETA -> pagoTarjeta;
            case PAYPAL -> pagoPayPal;
        };
    }
}
