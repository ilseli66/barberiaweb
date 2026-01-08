package com.skimobarber.identity.infrastructure.config;

import com.skimobarber.identity.domain.enums.TipoRol;
import com.skimobarber.identity.domain.model.Cliente;
import com.skimobarber.identity.domain.model.Genero;
import com.skimobarber.identity.domain.model.Persona;
import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.out.IClienteRepository;
import com.skimobarber.identity.domain.ports.out.IGeneroRepository;
import com.skimobarber.identity.domain.ports.out.IPersonaRepository;
import com.skimobarber.identity.domain.ports.out.IUsuarioRepository;
import com.skimobarber.identity.domain.ports.out.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Inicializador de datos de prueba para el entorno de desarrollo.
 * 
 * Este componente se ejecuta automáticamente al iniciar el microservicio
 * y crea usuarios de prueba si no existen.
 * 
 * Solo se activa con el perfil "dev" o cuando no hay perfil activo (default).
 * Para producción, usar el perfil "prod" y este inicializador no se ejecutará.
 * 
 * CREDENCIALES DE PRUEBA:
 * +--------------+------------------+---------------+
 * | Usuario      | Contraseña       | Rol           |
 * +--------------+------------------+---------------+
 * | admin        | admin123         | ADMINISTRADOR |
 * | barbero1     | barbero123       | EMPLEADO      |
 * | cliente1     | cliente123       | CLIENTE       |
 * +--------------+------------------+---------------+
 */
@Component
@Profile({"dev", "default"})
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final IGeneroRepository generoRepository;
    private final IPersonaRepository personaRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            IGeneroRepository generoRepository,
            IPersonaRepository personaRepository,
            IUsuarioRepository usuarioRepository,
            IClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder) {
        this.generoRepository = generoRepository;
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        log.info("============================================================");
        log.info("  Iniciando carga de datos de prueba - Barbería Axolotes");
        log.info("============================================================");

        try {
            // 1. Crear géneros
            Genero generoMasculino = createGeneroIfNotExists("Masculino");
            Genero generoFemenino = createGeneroIfNotExists("Femenino");
            createGeneroIfNotExists("No binario");

            // 2. Crear usuarios de prueba
            createTestUser(
                "admin",
                "Admin",
                "Sistema",
                "Principal",
                LocalDate.of(1990, 1, 15),
                "5551234567",
                "admin@test.skimobarber.com",
                generoMasculino.getId(),
                TipoRol.ADMINISTRADOR,
                "admin123",
                false // No es cliente
            );

            createTestUser(
                "barbero1",
                "Carlos",
                "Barbero",
                "Experto",
                LocalDate.of(1985, 6, 20),
                "5559876543",
                "barbero1@test.skimobarber.com",
                generoMasculino.getId(),
                TipoRol.EMPLEADO,
                "barbero123",
                false // No es cliente
            );

            createTestUser(
                "cliente1",
                "María",
                "Cliente",
                "Frecuente",
                LocalDate.of(1995, 3, 10),
                "5551112233",
                "cliente1@test.skimobarber.com",
                generoFemenino.getId(),
                TipoRol.CLIENTE,
                "cliente123",
                true // Es cliente, crear registro en tabla cliente
            );

            log.info("============================================================");
            log.info("  Datos de prueba cargados exitosamente");
            log.info("============================================================");
            log.info("  Usuarios disponibles:");
            log.info("    - admin / admin123 (ADMINISTRADOR)");
            log.info("    - barbero1 / barbero123 (EMPLEADO)");
            log.info("    - cliente1 / cliente123 (CLIENTE)");
            log.info("============================================================");

        } catch (Exception e) {
            log.error("Error al cargar datos de prueba: {}", e.getMessage());
            log.debug("Stack trace:", e);
        }
    }

    private Genero createGeneroIfNotExists(String nombre) {
        return generoRepository.findAll().stream()
                .filter(g -> g.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElseGet(() -> {
                    Genero genero = new Genero(null, nombre, true);
                    Genero saved = generoRepository.save(genero);
                    log.info("  ✓ Género creado: {}", nombre);
                    return saved;
                });
    }

    private void createTestUser(
            String login,
            String nombre,
            String primerApellido,
            String segundoApellido,
            LocalDate fechaNacimiento,
            String telefono,
            String email,
            Long generoId,
            TipoRol rol,
            String rawPassword,
            boolean crearCliente) {

        // Verificar si el usuario ya existe
        if (usuarioRepository.existsByLogin(login)) {
            log.info("  → Usuario '{}' ya existe, omitiendo...", login);
            return;
        }

        // Verificar si la persona ya existe por email
        if (personaRepository.existsByEmail(email)) {
            log.info("  → Persona con email '{}' ya existe, omitiendo...", email);
            return;
        }

        // Crear persona
        Persona persona = new Persona();
        persona.setGeneroId(generoId);
        persona.setNombre(nombre);
        persona.setPrimerApellido(primerApellido);
        persona.setSegundoApellido(segundoApellido);
        persona.setFechaNacimiento(fechaNacimiento);
        persona.setTelefono(telefono);
        persona.setEmail(email);

        Persona savedPersona = personaRepository.save(persona);
        log.info("  ✓ Persona creada: {} ({})", savedPersona.getNombreCompleto(), email);

        // Crear usuario con password encriptado
        Usuario usuario = new Usuario();
        usuario.setPersonaId(savedPersona.getId());
        usuario.setLogin(login);
        usuario.setRol(rol);
        usuario.setActivo(true);

        String encodedPassword = passwordEncoder.encode(rawPassword);
        usuarioRepository.saveWithPassword(usuario, encodedPassword);
        log.info("  ✓ Usuario creado: {} (rol: {})", login, rol);

        // Crear cliente si es necesario
        if (crearCliente) {
            Cliente cliente = new Cliente();
            cliente.setPersonaId(savedPersona.getId());
            cliente.setPuntosFidelidad(100);
            cliente.setNotasAlergias("Sin alergias conocidas");

            clienteRepository.save(cliente);
            log.info("  ✓ Cliente creado con 100 puntos de fidelidad");
        }
    }
}
