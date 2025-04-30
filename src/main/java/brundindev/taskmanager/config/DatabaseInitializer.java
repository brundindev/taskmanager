/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import brundindev.taskmanager.model.Task;
import brundindev.taskmanager.model.Usuario;
import brundindev.taskmanager.repository.TaskRepository;
import brundindev.taskmanager.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Buscar todas las tareas que no tienen usuario asignado
        List<Task> tasksWithoutUser = taskRepository.findAll().stream()
                .filter(task -> task.getUsuarioId() == null)
                .toList();
        
        if (!tasksWithoutUser.isEmpty()) {
            // Buscar al usuario administrador
            Usuario admin = usuarioRepository.findByUsername("admin")
                    .orElseThrow(() -> new RuntimeException("Usuario administrador no encontrado."));
            
            // Asignar el usuario administrador a todas las tareas que no tienen usuario
            for (Task task : tasksWithoutUser) {
                task.setUsuarioId(admin.getId());
                taskRepository.save(task);
            }
            
            System.out.println("Se asignaron " + tasksWithoutUser.size() + " tareas existentes al usuario administrador.");
        }
    }
} 
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */