package es.vives.ui;

import es.vives.application.dto.*;
import es.vives.application.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Menú de consola principal de la aplicación Vives House Legacy.
 * Permite gestionar las 5 entidades del dominio con CRUD completo.
 */
public class ConsoleMenu {
    private final CasaService casaService;
    private final UsuarioService usuarioService;
    private final EstanciaService estanciaService;
    private final GastoService gastoService;
    private final TareaService tareaService;
    private final Scanner scanner;

    public ConsoleMenu(CasaService casaService, UsuarioService usuarioService,
                       EstanciaService estanciaService, GastoService gastoService, TareaService tareaService) {
        this.casaService = casaService;
        this.usuarioService = usuarioService;
        this.estanciaService = estanciaService;
        this.gastoService = gastoService;
        this.tareaService = tareaService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n========================================");
            System.out.println("     VIVES HOUSE - LEGACY APP");
            System.out.println("========================================");
            System.out.println("1. Gestionar Casas");
            System.out.println("2. Gestionar Usuarios");
            System.out.println("3. Gestionar Estancias");
            System.out.println("4. Gestionar Gastos");
            System.out.println("5. Gestionar Tareas");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1": manageCasas(); break;
                case "2": manageUsuarios(); break;
                case "3": manageEstancias(); break;
                case "4": manageGastos(); break;
                case "5": manageTareas(); break;
                case "0":
                    exit = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Introduce un número del 0 al 5.");
            }
        }
    }

    // ===================== CASAS =====================
    private void manageCasas() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- GESTIÓN DE CASAS ---");
            System.out.println("1. Crear Casa");
            System.out.println("2. Listar Casas");
            System.out.println("3. Buscar Casa por ID");
            System.out.println("4. Actualizar Casa");
            System.out.println("5. Borrar Casa");
            System.out.println("0. Volver");
            System.out.print("Elige: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "1":
                    System.out.print("Nombre: "); String nombre = scanner.nextLine();
                    System.out.print("Dirección: "); String direccion = scanner.nextLine();
                    try {
                        CasaDTO creada = casaService.createCasa(new CasaDTO(null, nombre, direccion));
                        System.out.println("Casa creada con éxito: " + creada);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error de validación: " + e.getMessage());
                    }
                    break;
                case "2":
                    List<CasaDTO> casas = casaService.getAllCasas();
                    if (casas.isEmpty()) {
                        System.out.println("No hay casas registradas.");
                    } else {
                        casas.forEach(c -> System.out.println("  - " + c));
                    }
                    break;
                case "3":
                    System.out.print("ID Casa: "); String idBuscar = scanner.nextLine();
                    try {
                        casaService.getCasaById(idBuscar).ifPresentOrElse(
                                c -> System.out.println("Encontrada: " + c),
                                () -> System.out.println("Casa no encontrada.")
                        );
                    } catch (IllegalArgumentException e) {
                        System.out.println("ID inválido: " + e.getMessage());
                    }
                    break;
                case "4":
                    System.out.print("ID Casa a actualizar: "); String idCasa = scanner.nextLine();
                    System.out.print("Nuevo Nombre (vacío para no cambiar): "); String nNombre = scanner.nextLine();
                    System.out.print("Nueva Dirección (vacío para no cambiar): "); String nDir = scanner.nextLine();
                    try {
                        CasaDTO actualizada = casaService.updateCasa(idCasa,
                                new CasaDTO(null, nNombre.isEmpty() ? null : nNombre, nDir.isEmpty() ? null : nDir));
                        System.out.println("Casa actualizada: " + actualizada);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "5":
                    System.out.print("ID Casa a borrar: "); String idB = scanner.nextLine();
                    try {
                        casaService.deleteCasa(idB);
                        System.out.println("Casa borrada correctamente.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "0": back = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    // ===================== USUARIOS =====================
    private void manageUsuarios() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Crear Usuario");
            System.out.println("2. Listar Usuarios");
            System.out.println("3. Buscar Usuario por ID");
            System.out.println("4. Actualizar Usuario");
            System.out.println("5. Borrar Usuario");
            System.out.println("0. Volver");
            System.out.print("Elige: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "1":
                    System.out.print("Nombre: "); String nombre = scanner.nextLine();
                    System.out.print("Email: "); String email = scanner.nextLine();
                    System.out.print("Password: "); String pass = scanner.nextLine();
                    System.out.print("Rol (admin/miembro): "); String rol = scanner.nextLine();
                    try {
                        UsuarioDTO creado = usuarioService.createUsuario(new UsuarioDTO(null, nombre, email, pass, rol));
                        System.out.println("Usuario creado con éxito: " + creado);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error de validación: " + e.getMessage());
                    }
                    break;
                case "2":
                    List<UsuarioDTO> usuarios = usuarioService.getAllUsuarios();
                    if (usuarios.isEmpty()) {
                        System.out.println("No hay usuarios registrados.");
                    } else {
                        usuarios.forEach(u -> System.out.println("  - " + u));
                    }
                    break;
                case "3":
                    System.out.print("ID Usuario: "); String idBuscar = scanner.nextLine();
                    try {
                        usuarioService.getUsuarioById(idBuscar).ifPresentOrElse(
                                u -> System.out.println("Encontrado: " + u),
                                () -> System.out.println("Usuario no encontrado.")
                        );
                    } catch (IllegalArgumentException e) {
                        System.out.println("ID inválido: " + e.getMessage());
                    }
                    break;
                case "4":
                    System.out.print("ID Usuario: "); String idU = scanner.nextLine();
                    System.out.print("Nuevo Nombre (vacío para no cambiar): "); String nN = scanner.nextLine();
                    System.out.print("Nuevo Email (vacío para no cambiar): "); String nE = scanner.nextLine();
                    System.out.print("Nueva Password (vacío para no cambiar): "); String nP = scanner.nextLine();
                    System.out.print("Nuevo Rol (vacío para no cambiar): "); String nR = scanner.nextLine();
                    try {
                        UsuarioDTO actualizado = usuarioService.updateUsuario(idU,
                                new UsuarioDTO(null, nN.isEmpty() ? null : nN, nE.isEmpty() ? null : nE,
                                        nP.isEmpty() ? null : nP, nR.isEmpty() ? null : nR));
                        System.out.println("Usuario actualizado: " + actualizado);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "5":
                    System.out.print("ID Usuario a borrar: "); String idB = scanner.nextLine();
                    try {
                        usuarioService.deleteUsuario(idB);
                        System.out.println("Usuario borrado correctamente.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "0": back = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    // ===================== ESTANCIAS =====================
    private void manageEstancias() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- GESTIÓN DE ESTANCIAS ---");
            System.out.println("1. Crear Estancia (vincular usuario a casa)");
            System.out.println("2. Listar Estancias");
            System.out.println("3. Buscar Estancia por ID");
            System.out.println("4. Actualizar Estancia (fecha salida)");
            System.out.println("5. Borrar Estancia");
            System.out.println("0. Volver");
            System.out.print("Elige: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "1":
                    System.out.print("ID Casa: "); String idC = scanner.nextLine();
                    System.out.print("ID Usuario: "); String idUs = scanner.nextLine();
                    System.out.print("Fecha entrada (YYYY-MM-DD, vacío = hoy): "); String fechaEnt = scanner.nextLine();
                    LocalDate entrada = fechaEnt.isEmpty() ? LocalDate.now() : parseDate(fechaEnt);
                    if (entrada == null) break;
                    try {
                        EstanciaDTO creada = estanciaService.createEstancia(
                                new EstanciaDTO(null, idC, idUs, entrada, null));
                        System.out.println("Estancia creada con éxito: " + creada);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error de validación: " + e.getMessage());
                    }
                    break;
                case "2":
                    List<EstanciaDTO> estancias = estanciaService.getAllEstancias();
                    if (estancias.isEmpty()) {
                        System.out.println("No hay estancias registradas.");
                    } else {
                        estancias.forEach(e -> System.out.println("  - " + e));
                    }
                    break;
                case "3":
                    System.out.print("ID Estancia: "); String idBuscar = scanner.nextLine();
                    try {
                        estanciaService.getEstanciaById(idBuscar).ifPresentOrElse(
                                e -> System.out.println("Encontrada: " + e),
                                () -> System.out.println("Estancia no encontrada.")
                        );
                    } catch (IllegalArgumentException e) {
                        System.out.println("ID inválido: " + e.getMessage());
                    }
                    break;
                case "4":
                    System.out.print("ID Estancia a actualizar: "); String idEst = scanner.nextLine();
                    System.out.print("Nueva fecha de salida (YYYY-MM-DD): "); String fechaSal = scanner.nextLine();
                    LocalDate salida = parseDate(fechaSal);
                    if (salida == null) break;
                    try {
                        EstanciaDTO actualizada = estanciaService.updateEstancia(idEst,
                                new EstanciaDTO(null, null, null, null, salida));
                        System.out.println("Estancia actualizada: " + actualizada);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "5":
                    System.out.print("ID Estancia a borrar: "); String idB = scanner.nextLine();
                    try {
                        estanciaService.deleteEstancia(idB);
                        System.out.println("Estancia borrada correctamente.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "0": back = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    // ===================== GASTOS =====================
    private void manageGastos() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- GESTIÓN DE GASTOS ---");
            System.out.println("1. Crear Gasto");
            System.out.println("2. Listar Gastos");
            System.out.println("3. Buscar Gasto por ID");
            System.out.println("4. Actualizar Gasto");
            System.out.println("5. Borrar Gasto");
            System.out.println("0. Volver");
            System.out.print("Elige: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "1":
                    System.out.print("ID Casa: "); String idC = scanner.nextLine();
                    System.out.print("ID Usuario (pagador): "); String idUs = scanner.nextLine();
                    System.out.print("Descripción: "); String desc = scanner.nextLine();
                    System.out.print("Cantidad: ");
                    double cant;
                    try {
                        cant = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Introduce un número válido para la cantidad.");
                        break;
                    }
                    try {
                        GastoDTO creado = gastoService.createGasto(
                                new GastoDTO(null, desc, cant, LocalDate.now(), idC, idUs));
                        System.out.println("Gasto creado con éxito: " + creado);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error de validación: " + e.getMessage());
                    }
                    break;
                case "2":
                    List<GastoDTO> gastos = gastoService.getAllGastos();
                    if (gastos.isEmpty()) {
                        System.out.println("No hay gastos registrados.");
                    } else {
                        gastos.forEach(g -> System.out.println("  - " + g));
                    }
                    break;
                case "3":
                    System.out.print("ID Gasto: "); String idBuscar = scanner.nextLine();
                    try {
                        gastoService.getGastoById(idBuscar).ifPresentOrElse(
                                g -> System.out.println("Encontrado: " + g),
                                () -> System.out.println("Gasto no encontrado.")
                        );
                    } catch (IllegalArgumentException e) {
                        System.out.println("ID inválido: " + e.getMessage());
                    }
                    break;
                case "4":
                    System.out.print("ID Gasto a actualizar: "); String idGasto = scanner.nextLine();
                    System.out.print("Nueva Descripción (vacío para no cambiar): "); String nDesc = scanner.nextLine();
                    System.out.print("Nueva Cantidad (0 para no cambiar): ");
                    double nCant;
                    try {
                        nCant = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Introduce un número válido.");
                        break;
                    }
                    try {
                        GastoDTO actualizado = gastoService.updateGasto(idGasto,
                                new GastoDTO(null, nDesc.isEmpty() ? null : nDesc, nCant, null, null, null));
                        System.out.println("Gasto actualizado: " + actualizado);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "5":
                    System.out.print("ID Gasto a borrar: "); String idB = scanner.nextLine();
                    try {
                        gastoService.deleteGasto(idB);
                        System.out.println("Gasto borrado correctamente.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "0": back = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    // ===================== TAREAS =====================
    private void manageTareas() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- GESTIÓN DE TAREAS ---");
            System.out.println("1. Crear Tarea");
            System.out.println("2. Listar Tareas");
            System.out.println("3. Buscar Tarea por ID");
            System.out.println("4. Actualizar Tarea");
            System.out.println("5. Borrar Tarea");
            System.out.println("0. Volver");
            System.out.print("Elige: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "1":
                    System.out.print("ID Casa: "); String idC = scanner.nextLine();
                    System.out.print("ID Usuario Asignado (vacío si no asignado): "); String idUs = scanner.nextLine();
                    System.out.print("Descripción: "); String desc = scanner.nextLine();
                    try {
                        TareaDTO creada = tareaService.createTarea(
                                new TareaDTO(null, desc, "PENDIENTE", idC, idUs.isEmpty() ? null : idUs));
                        System.out.println("Tarea creada con éxito: " + creada);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error de validación: " + e.getMessage());
                    }
                    break;
                case "2":
                    List<TareaDTO> tareas = tareaService.getAllTareas();
                    if (tareas.isEmpty()) {
                        System.out.println("No hay tareas registradas.");
                    } else {
                        tareas.forEach(t -> System.out.println("  - " + t));
                    }
                    break;
                case "3":
                    System.out.print("ID Tarea: "); String idBuscar = scanner.nextLine();
                    try {
                        tareaService.getTareaById(idBuscar).ifPresentOrElse(
                                t -> System.out.println("Encontrada: " + t),
                                () -> System.out.println("Tarea no encontrada.")
                        );
                    } catch (IllegalArgumentException e) {
                        System.out.println("ID inválido: " + e.getMessage());
                    }
                    break;
                case "4":
                    System.out.print("ID Tarea a actualizar: "); String idTarea = scanner.nextLine();
                    System.out.print("Nueva Descripción (vacío para no cambiar): "); String nDesc = scanner.nextLine();
                    System.out.print("Nuevo Estado (PENDIENTE/EN_PROGRESO/COMPLETADA, vacío para no cambiar): ");
                    String nEstado = scanner.nextLine();
                    System.out.print("Nuevo ID Usuario Asignado (vacío para no cambiar): ");
                    String nIdUs = scanner.nextLine();
                    try {
                        TareaDTO actualizada = tareaService.updateTarea(idTarea,
                                new TareaDTO(null, nDesc.isEmpty() ? null : nDesc,
                                        nEstado.isEmpty() ? null : nEstado, null,
                                        nIdUs.isEmpty() ? null : nIdUs));
                        System.out.println("Tarea actualizada: " + actualizada);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "5":
                    System.out.print("ID Tarea a borrar: "); String idB = scanner.nextLine();
                    try {
                        tareaService.deleteTarea(idB);
                        System.out.println("Tarea borrada correctamente.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "0": back = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Parsea una fecha en formato YYYY-MM-DD. Devuelve null y muestra error si el formato es inválido.
     */
    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha inválido. Usa YYYY-MM-DD.");
            return null;
        }
    }
}
