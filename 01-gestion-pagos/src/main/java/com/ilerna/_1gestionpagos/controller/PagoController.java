package com.ilerna._1gestionpagos.controller;

import com.ilerna._1gestionpagos.model.Pago;
import com.ilerna._1gestionpagos.service.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    /**
     * POST /api/pagos - Procesa un nuevo pago.
     */
    @PostMapping
    public ResponseEntity<Pago> procesarPago(@RequestBody Pago pago) {
        Pago resultado = pagoService.procesarPago(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    /**
     * GET /api/pagos/{id} - Consulta un pago por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPago(@PathVariable Long id) {
        return pagoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/pagos - Lista todos los pagos.
     */
    @GetMapping
    public ResponseEntity<List<Pago>> listarPagos() {
        List<Pago> pagos = pagoService.listarPagos();
        return ResponseEntity.ok(pagos);
    }
}
