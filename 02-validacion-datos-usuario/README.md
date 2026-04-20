# Caso 2: Validación de Datos de Usuario (Librería)

## Descripción

Librería Java pura que proporciona validación de datos de usuario para una plataforma e-commerce.

**Tipo:** Librería Java + Maven (multi-módulo)  
**Persistencia:** Sin persistencia (lógica pura) + fichero de configuración de reglas

**Justificación arquitectónica:** Es lógica de validación pura sin estado, usada en todos los formularios. Embeber como librería elimina latencia de red y simplifica el despliegue. Máxima reutilización al importarla como dependencia Maven. No necesita escalar independientemente.

---

## Estructura del proyecto

```
02-validacion-datos-usuario/
├── pom.xml                                        ← Parent multi-módulo
├── validacion-lib/                                ← Librería (jar instalable)
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/ilerna/validacion/
│       │   ├── ResultadoValidacion.java           ← Resultado de validación (válido/inválido + mensaje)
│       │   └── ValidadorUsuario.java              ← 4 validadores estáticos
│       └── resources/
│           └── reglas-validacion.properties       ← Reglas configurables
└── validacion-ejemplo/                            ← App de consola de demostración
    ├── pom.xml                                    ← Depende de validacion-lib
    └── src/main/java/com/ilerna/ejemplo/
        └── Main.java
```

---

## Validadores implementados

| Método                    | Descripción                                                           |
|---------------------------|-----------------------------------------------------------------------|
| `validarEmail(String)`    | Expresión regular RFC-compatible                                      |
| `validarPassword(String)` | Longitud mínima (configurable), mayúscula, número y carácter especial |
| `validarDNI(String)`      | Formato 8 dígitos + letra de control (módulo 23)                      |
| `validarNIE(String)`      | Prefijo X/Y/Z + 7 dígitos + letra de control (módulo 23)              |

Todos devuelven un `ResultadoValidacion` con `isValido()` y `getMensajeError()`.

---

## Configuración

Las reglas configurables se definen en `validacion-lib/src/main/resources/reglas-validacion.properties`:

```properties
# Longitud mínima de la contraseña
password.min.longitud=8
```

---

## Compilar e instalar la librería

```bash
# Desde 02-validacion-datos-usuario/
mvn install
```

---

## Ejecutar el ejemplo

```bash
cd validacion-ejemplo
mvn exec:java
```

### Salida esperada

```
=== Validación de Datos de Usuario ===

--- Email ---
  Email válido                   → VÁLIDO
  Sin @                          → INVÁLIDO: El email no tiene un formato válido.
  ...

--- Contraseña ---
  Contraseña válida              → VÁLIDO
  Demasiado corta                → INVÁLIDO: La contraseña debe tener al menos 8 caracteres.
  ...

--- DNI ---
  DNI válido (12345678Z)         → VÁLIDO
  Letra incorrecta               → INVÁLIDO: La letra del DNI no es correcta. Se esperaba: Z
  ...

--- NIE ---
  NIE válido (X1234567L)         → VÁLIDO
  Letra incorrecta               → INVÁLIDO: La letra del NIE no es correcta. Se esperaba: L
  ...
```

---

## Usar la librería en otro proyecto

Tras ejecutar `mvn install`, añadir al `pom.xml` del proyecto consumidor:

```xml
<dependency>
    <groupId>com.ilerna</groupId>
    <artifactId>validacion-lib</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
