/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */
package brundindev.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brundindev.taskmanager.model.Usuario;
import brundindev.taskmanager.repository.UsuarioRepository;
import brundindev.taskmanager.service.UsuarioService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping
    public String profilePage(Model model) {
        return "profile";
    }
    
    @PostMapping("/change-password")
    @Transactional
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) {
        
        // Obtener el usuario actual
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Buscar el usuario en la base de datos
        Usuario usuario = usuarioService.buscarPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(currentPassword, usuario.getPassword())) {
            redirectAttributes.addFlashAttribute("errorMessage", "La contraseña actual es incorrecta");
            return "redirect:/profile";
        }
        
        // Verificar que las nuevas contraseñas coincidan
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Las nuevas contraseñas no coinciden");
            return "redirect:/profile";
        }
        
        // Verificar que la nueva contraseña tenga al menos 8 caracteres
        if (newPassword.length() < 8) {
            redirectAttributes.addFlashAttribute("errorMessage", "La nueva contraseña debe tener al menos 8 caracteres");
            return "redirect:/profile";
        }
        
        try {
            // Actualizar la contraseña utilizando el servicio para asegurar que se use el mismo método de codificación
            // que se usa en el registro y en la autenticación
            usuario.setPassword(newPassword); // Establecer la contraseña sin codificar
            usuarioService.registrarUsuario(usuario); // El servicio codificará la contraseña antes de guardarla
            
            redirectAttributes.addFlashAttribute("successMessage", "Contraseña actualizada correctamente. Por favor, inicie sesión nuevamente con su nueva contraseña.");
            return "redirect:/logout"; // Cerrar sesión para forzar al usuario a iniciar sesión con nueva contraseña
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar la contraseña: " + e.getMessage());
            return "redirect:/profile";
        }
    }
} 
/*
 * ========================================================
 * 2025 © copyright  @brundindev
 * ========================================================
 */