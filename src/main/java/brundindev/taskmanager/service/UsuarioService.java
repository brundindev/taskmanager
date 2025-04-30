/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.service;

import java.util.Optional;

import brundindev.taskmanager.model.Usuario;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);
    Optional<Usuario> buscarPorUsername(String username);
    boolean existeUsername(String username);
    void inicializarUsuarioAdmin();
} 
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */