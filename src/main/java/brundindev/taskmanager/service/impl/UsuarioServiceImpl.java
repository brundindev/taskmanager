/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import brundindev.taskmanager.model.Usuario;
import brundindev.taskmanager.repository.UsuarioRepository;
import brundindev.taskmanager.service.UsuarioService;
import jakarta.annotation.PostConstruct;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        // Codificar la contraseña antes de guardarla
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public boolean existeUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
    
    @Override
    @PostConstruct
    public void inicializarUsuarioAdmin() {
        try {
            // Verificar si ya existe el usuario admin
            if (!existeUsername("admin")) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword("admin"); // Se codificará en el método registrarUsuario
                admin.setRol("ROLE_ADMIN");
                admin.setNombre("Administrador");
                admin.setEmail("admin@brundindev.com");
                
                registrarUsuario(admin);
                System.out.println("Usuario administrador creado con éxito.");
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar usuario admin: " + e.getMessage());
        }
    }
} 
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */