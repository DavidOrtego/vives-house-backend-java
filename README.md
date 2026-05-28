# Reto Backend Java (1º DAW) - Vives House Legacy

Este proyecto es una aplicación de consola en Java puro diseñada como si fuera un sistema *legacy*, pero implementando buenas prácticas de Ingeniería de Software. Sirve como núcleo backend (simulado sin servidor web) para el dominio de gestión de pisos compartidos **Vives House**.

## 📖 Dominio de la Aplicación

El sistema gestiona la convivencia y administración de una casa compartida. Se han implementado las siguientes 5 entidades de dominio principales, todas identificadas por **UUIDs**:

1. **Casa**: Representa un hogar/piso con su nombre y dirección.
2. **Usuario**: Representa a un inquilino, que puede tener rol de administrador o miembro.
3. **Estancia**: Relación que indica qué `Usuario` vive en qué `Casa` y durante qué periodo (fecha de entrada y salida).
4. **Gasto**: Registro financiero de un pago realizado por un `Usuario` en una `Casa` concreta, con su cantidad y descripción.
5. **Tarea**: Tarea del hogar asignada a un `Usuario` dentro de una `Casa`, con seguimiento de su estado (pendiente, completada, etc.).

## 🏗 Arquitectura del Sistema

Se ha seguido una **arquitectura en capas (Clean Architecture / N-Tier)** estricta:

```text
src/main/java/es/vives/
├── ui/                 # Interfaz de usuario (Consola interactiva)
├── application/
│   ├── dto/            # Data Transfer Objects (no exponen entidades al UI)
│   └── service/        # Casos de uso y reglas de negocio
├── domain/             # Entidades de negocio puras (POJOs sin frameworks)
│   └── repository/     # Interfaces de repositorios (Contratos)
├── infrastructure/
│   ├── persistence/    # Implementaciones: SQLite y Memoria (HashMap)
│   └── factory/        # Implementación del Patrón Abstract Factory
└── Main.java           # Punto de entrada y composición manual (DI)
```

## ⚙️ Persistencia y Patrón Factory

La aplicación cumple el requisito crítico de soportar múltiples bases de datos sin cambiar el código de negocio:
- Utiliza **SQLite** como motor de persistencia principal en disco. Genera automáticamente un archivo `vives_house.db`. Se eligió SQLite por ser embebido y no requerir de un servidor externo instalado.
- Utiliza estructuras de memoria (`HashMap`) como base de datos alternativa (ideal para testing muy rápido).

Para cambiar de base de datos, simplemente modifica el archivo `src/main/resources/application.properties`:
```properties
db.type=memory
# db.type=sqlite
```

El patrón **Abstract Factory** (`RepositoryFactoryProvider`, `SQLiteRepositoryFactory`, `MemoryRepositoryFactory`) se encarga de inyectar las dependencias adecuadas en tiempo de ejecución.

## 🚀 Cómo Ejecutar la Aplicación

### Requisitos previos
- Java 17 o superior.
- Maven 3.8+ (opcional si usas IDE).

### Compilar y Ejecutar Tests (TDD)
El desarrollo siguió **TDD**. Puedes correr los tests automatizados (que usan JUnit 5 y Mockito) con:
```bash
mvn clean test
```

### Ejecutar la Aplicación de Consola
1. Compila el proyecto:
```bash
mvn clean compile
```
2. Ejecuta la clase `Main`:
```bash
mvn exec:java -Dexec.mainClass="es.vives.Main"
```
O simplemente ejecuta la clase `es.vives.Main` desde tu IDE favorito (IntelliJ IDEA, Eclipse, VSCode).

## 💡 Prácticas Profesionales Implementadas

- **DTOs estrictos**: La capa UI interactúa con DTOs devueltos por la capa Application, asegurando que las Entidades Domain nunca se "cuelen" en la consola.
- **TDD (Test Driven Development)**: Cobertura completa en la lógica de todos los Servicios usando Mockito, y tests directos contra todos los repositorios en Memoria.
- **SLF4J**: Sistema estándar de Logging para registrar los accesos a Base de Datos y excepciones críticas.
- **UUIDs**: Eliminados los incrementales para garantizar sistemas distribuidos modernos y seguros.
