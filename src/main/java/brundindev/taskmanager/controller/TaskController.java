/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brundindev.taskmanager.dto.CreateTaskDTO;
import brundindev.taskmanager.dto.TaskDTO;
import brundindev.taskmanager.model.TaskStatus;
import brundindev.taskmanager.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // GET /api/tasks - Obtener todas las tareas
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasksForCurrentUser();
        return ResponseEntity.ok(tasks);
    }

    // GET /api/tasks/{id} - Obtener tarea por ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        TaskDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // POST /api/tasks - Crear nueva tarea
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody CreateTaskDTO createTaskDTO) {
        TaskDTO createdTask = taskService.createTask(createTaskDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    // PUT /api/tasks/{id} - Actualizar tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody CreateTaskDTO updateTaskDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, updateTaskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    // DELETE /api/tasks/{id} - Eliminar tarea
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/tasks/grouped-by-status - Demostrar Map
    @GetMapping("/grouped-by-status")
    public ResponseEntity<Map<TaskStatus, List<TaskDTO>>> getTasksGroupedByStatus() {
        Map<TaskStatus, List<TaskDTO>> groupedTasks = taskService.getTasksGroupedByStatusForCurrentUser();
        return ResponseEntity.ok(groupedTasks);
    }

    // PATCH /api/tasks/{id}/status - Actualizar solo el estado de una tarea
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(
            @PathVariable Long id, 
            @RequestBody Map<String, String> statusUpdate) {
        
        // Validar que el estado proporcionado sea válido
        String newStatus = statusUpdate.get("status");
        if (newStatus == null) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            TaskStatus taskStatus = TaskStatus.valueOf(newStatus);
            TaskDTO updatedTask = taskService.updateTaskStatus(id, taskStatus);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Aquí podrías añadir un endpoint para demostrar Set si lo implementas en el servicio
    // @GetMapping("/titles") ...
}
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */