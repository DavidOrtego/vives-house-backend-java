package es.vives;

import es.vives.application.service.CasaService;
import es.vives.application.service.EstanciaService;
import es.vives.application.service.GastoService;
import es.vives.application.service.TareaService;
import es.vives.application.service.UsuarioService;
import es.vives.infrastructure.factory.RepositoryFactory;
import es.vives.infrastructure.factory.RepositoryFactoryProvider;
import es.vives.ui.ConsoleMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Iniciando aplicación Vives House Legacy...");

        // Inyección de dependencias manual usando Factory
        RepositoryFactory repositoryFactory = RepositoryFactoryProvider.getFactory();

        CasaService casaService = new CasaService(repositoryFactory.createCasaRepository());
        UsuarioService usuarioService = new UsuarioService(repositoryFactory.createUsuarioRepository());
        EstanciaService estanciaService = new EstanciaService(repositoryFactory.createEstanciaRepository());
        GastoService gastoService = new GastoService(repositoryFactory.createGastoRepository());
        TareaService tareaService = new TareaService(repositoryFactory.createTareaRepository());

        ConsoleMenu menu = new ConsoleMenu(casaService, usuarioService, estanciaService, gastoService, tareaService);
        menu.showMenu();
        
        logger.info("Aplicación finalizada.");
    }
}
