/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import brundindev.taskmanager.model.Task;
import brundindev.taskmanager.model.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Spring Data JPA genera las implementaciones CRUD básicas
    // Podemos añadir métodos de consulta personalizados si es necesario
    List<Task> findByStatus(TaskStatus status);
    
    // Buscar tareas por usuarioId
    List<Task> findByUsuarioId(Long usuarioId);
    
    // Buscar tareas por usuarioId y status
    List<Task> findByUsuarioIdAndStatus(Long usuarioId, TaskStatus status);
}
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */