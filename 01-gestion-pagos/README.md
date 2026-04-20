# Caso 1: Gestión de Pagos (Microservicio)

## Descripción

Microservicio Spring Boot que gestiona el procesamiento de pagos para una plataforma e-commerce.

**Tipo:** Microservicio Spring Boot + Maven  
**Puerto:** 8081  
**Persistencia:** BBDD Relacional (H2 en memoria)

**Justificación arquitectónica:** Debe ser consumido por múltiples aplicaciones (web, móvil, API externa), lo que exige una API REST independiente. Las transacciones financieras requieren consistencia ACID (relacional). Escalabilidad independiente del resto del sistema y despliegue aislado para cumplir requisitos de seguridad PCI.

---

## Patrón de diseño: Strategy

El procesamiento de pagos implementa el patrón **Strategy** para soportar múltiples pasarelas:

- `PasarelaPago` — interfaz común
- `PagoTarjeta` — implementación para tarjeta de crédito/débito
- `PagoPayPal` — implementación para PayPal

`PagoService` selecciona automáticamente la pasarela correcta según el método indicado en la petición.

---

## Estructura del proyecto

```
01-gestion-pagos/
└── src/main/java/com/ilerna/_1gestionpagos/
    ├── model/
    │   └── Pago.java              ← Entidad JPA (monto, moneda, metodo, estado, fechaCreacion)
    ├── repository/
    │   └── PagoRepository.java    ← Spring Data JPA
    ├── service/
    │   ├── PagoService.java       ← Lógica de negocio + selección de pasarela
    │   └── pasarela/
    │       ├── PasarelaPago.java  ← Interfaz Strategy
    │       ├── PagoTarjeta.java   ← Impl. tarjeta (siempre COMPLETADO)
    │       └── PagoPayPal.java    ← Impl. PayPal (COMPLETADO si monto > 0)
    └── controller/
        └── PagoController.java    ← REST: POST /api/pagos, GET /api/pagos/{id}, GET /api/pagos
```

---

## Endpoints REST

| Método | Ruta               | Descripción          |
|--------|--------------------|----------------------|
| POST   | `/api/pagos`       | Procesar un pago     |
| GET    | `/api/pagos/{id}`  | Consultar un pago    |
| GET    | `/api/pagos`       | Listar todos los pagos |

---

## Arrancar el servicio

```bash
mvn spring-boot:run
```

---

## Comandos de prueba

```bash
# Procesar pago con tarjeta
curl -X POST http://localhost:8081/api/pagos \
  -H "Content-Type: application/json" \
  -d '{"monto":99.99,"moneda":"EUR","metodo":"TARJETA"}'

# Procesar pago con PayPal
curl -X POST http://localhost:8081/api/pagos \
  -H "Content-Type: application/json" \
  -d '{"monto":49.50,"moneda":"EUR","metodo":"PAYPAL"}'

# Consultar pago por ID
curl http://localhost:8081/api/pagos/1

# Listar todos los pagos
curl http://localhost:8081/api/pagos
```

---

## Consola H2

La consola web de H2 está disponible en desarrollo en:

**URL:** `http://localhost:8081/h2-console`  
**JDBC URL:** `jdbc:h2:mem:pagosdb`  
**Usuario:** `sa`  
**Contraseña:** *(vacía)*
