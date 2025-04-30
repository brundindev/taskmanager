/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import brundindev.taskmanager.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
} 
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */