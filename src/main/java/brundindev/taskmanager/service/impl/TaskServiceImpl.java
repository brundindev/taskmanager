/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import brundindev.taskmanager.dto.CreateTaskDTO;
import brundindev.taskmanager.dto.TaskDTO;
import brundindev.taskmanager.model.Task;
import brundindev.taskmanager.model.TaskStatus;
import brundindev.taskmanager.model.Usuario;
import brundindev.taskmanager.repository.TaskRepository;
import brundindev.taskmanager.repository.UsuarioRepository;
import brundindev.taskmanager.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public List<TaskDTO> getAllTasksForCurrentUser() {
        Long usuarioId = getCurrentUserId();
        List<Task> tasks = taskRepository.findByUsuarioId(usuarioId);
        return tasks.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));
        
        // Verificar que la tarea pertenece al usuario autenticado
        if (!task.getUsuarioId().equals(getCurrentUserId())) {
            throw new AccessDeniedException("No tienes permiso para acceder a esta tarea");
        }
        
        return mapToDTO(task);
    }

    @Override
    public TaskDTO createTask(CreateTaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setDueDate(taskDTO.getDueDate());
        task.setUsuarioId(getCurrentUserId());
        
        Task savedTask = taskRepository.save(task);
        return mapToDTO(savedTask);
    }

    @Override
    public TaskDTO updateTask(Long id, CreateTaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));
        
        // Verificar que la tarea pertenece al usuario autenticado
        if (!task.getUsuarioId().equals(getCurrentUserId())) {
            throw new AccessDeniedException("No tienes permiso para modificar esta tarea");
        }
        
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setDueDate(taskDTO.getDueDate());
        
        Task updatedTask = taskRepository.save(task);
        return mapToDTO(updatedTask);
    }

    @Override
    public TaskDTO updateTaskStatus(Long id, TaskStatus newStatus) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));
        
        // Verificar que la tarea pertenece al usuario autenticado
        if (!task.getUsuarioId().equals(getCurrentUserId())) {
            throw new AccessDeniedException("No tienes permiso para modificar esta tarea");
        }
        
        // Actualizar solo el estado de la tarea
        task.setStatus(newStatus);
        Task updatedTask = taskRepository.save(task);
        return mapToDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));
        
        // Verificar que la tarea pertenece al usuario autenticado
        if (!task.getUsuarioId().equals(getCurrentUserId())) {
            throw new AccessDeniedException("No tienes permiso para eliminar esta tarea");
        }
        
        taskRepository.deleteById(id);
    }

    @Override
    public Map<TaskStatus, List<TaskDTO>> getTasksGroupedByStatusForCurrentUser() {
        List<TaskDTO> allTasks = getAllTasksForCurrentUser();
        Map<TaskStatus, List<TaskDTO>> groupedTasks = new HashMap<>();
        
        for (TaskStatus status : TaskStatus.values()) {
            groupedTasks.put(status, new ArrayList<>());
        }
        
        for (TaskDTO task : allTasks) {
            groupedTasks.get(task.getStatus()).add(task);
        }
        
        return groupedTasks;
    }
    
    private TaskDTO mapToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setDueDate(task.getDueDate());
        return dto;
    }
    
    // Método auxiliar para obtener el ID del usuario autenticado actual
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        return usuario.getId();
    }
}
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */