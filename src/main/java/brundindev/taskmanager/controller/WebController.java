/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import brundindev.taskmanager.dto.CreateTaskDTO;
import brundindev.taskmanager.dto.TaskDTO;
import brundindev.taskmanager.model.TaskStatus;
import brundindev.taskmanager.service.TaskService;

@Controller
public class WebController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String home(Model model) {
        List<TaskDTO> tasks = taskService.getAllTasksForCurrentUser();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/tasks/new")
    public String newTaskForm(Model model) {
        model.addAttribute("task", new CreateTaskDTO());
        model.addAttribute("statuses", TaskStatus.values());
        return "task-form";
    }

    @PostMapping("/tasks/save")
    public String saveTask(@ModelAttribute CreateTaskDTO task) {
        taskService.createTask(task);
        return "redirect:/";
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model) {
        TaskDTO taskDTO = taskService.getTaskById(id);
        CreateTaskDTO task = new CreateTaskDTO();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setDueDate(taskDTO.getDueDate());
        
        model.addAttribute("task", task);
        model.addAttribute("taskId", id);
        model.addAttribute("statuses", TaskStatus.values());
        return "task-edit";
    }

    @PostMapping("/tasks/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute CreateTaskDTO task) {
        taskService.updateTask(id, task);
        return "redirect:/";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }
    
    @GetMapping("/h2")
    public String h2Console() {
        return "redirect:/h2-console";
    }
} 
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */