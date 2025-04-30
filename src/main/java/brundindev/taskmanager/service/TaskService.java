/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.service;

import java.util.List;
import java.util.Map;

import brundindev.taskmanager.dto.CreateTaskDTO;
import brundindev.taskmanager.dto.TaskDTO;
import brundindev.taskmanager.model.TaskStatus;

public interface TaskService {
    List<TaskDTO> getAllTasksForCurrentUser();
    TaskDTO getTaskById(Long id);
    TaskDTO createTask(CreateTaskDTO taskDTO);
    TaskDTO updateTask(Long id, CreateTaskDTO taskDTO);
    TaskDTO updateTaskStatus(Long id, TaskStatus newStatus);
    void deleteTask(Long id);
    Map<TaskStatus, List<TaskDTO>> getTasksGroupedByStatusForCurrentUser();
    // Método para demostrar Set (ejemplo: obtener títulos únicos)
    // Set<String> getAllTaskTitles(); // Set
}
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */