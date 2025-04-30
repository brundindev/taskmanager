/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.dto;

import java.time.LocalDate;

import brundindev.taskmanager.model.TaskStatus;
// Puedes añadir validaciones de Spring Validation aquí si quieres (@NotNull, @Size, etc.)

public class CreateTaskDTO { // Para peticiones POST/PUT
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    
    // Getters y Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */