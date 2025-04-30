/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brundindev.taskmanager.dto.RegistroDTO;
import brundindev.taskmanager.model.Usuario;
import brundindev.taskmanager.service.UsuarioService;

@Controller
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    
    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("usuario", new RegistroDTO());
        return "registro";
    }
    
    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute("usuario") RegistroDTO registroDTO,
                                  RedirectAttributes redirectAttributes) {
        
        // Verificar si el usuario ya existe
        if (usuarioService.existeUsername(registroDTO.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "El nombre de usuario ya está en uso");
            return "redirect:/registro";
        }
        
        // Verificar que las contraseñas coincidan
        if (!registroDTO.getPassword().equals(registroDTO.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/registro";
        }
        
        // Crear y guardar el nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(registroDTO.getUsername());
        nuevoUsuario.setPassword(registroDTO.getPassword()); // Se codificará en el servicio
        nuevoUsuario.setNombre(registroDTO.getNombre());
        nuevoUsuario.setEmail(registroDTO.getEmail());
        
        usuarioService.registrarUsuario(nuevoUsuario);
        
        redirectAttributes.addFlashAttribute("success", "Registro exitoso. Por favor, inicia sesión.");
        return "redirect:/login";
    }
} 
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */