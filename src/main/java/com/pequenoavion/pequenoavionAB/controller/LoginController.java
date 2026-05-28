package com.pequenoavion.pequenoavionAB.controller;

import com.pequenoavion.pequenoavionAB.model.Usuario;
import com.pequenoavion.pequenoavionAB.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador de Accesos (LoginController)
 * Se encarga de manejar las "puertas de entrada" a la tienda:
 * el inicio de sesión para usuarios existentes y el registro para clientes nuevos.
 */
@Controller
public class LoginController {

    // --- INYECCIÓN DE DEPENDENCIAS ---
    
    // Nos permite conectarnos a la tabla de usuarios en la base de datos MySQL
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Herramienta de seguridad que transforma las contraseñas en texto ilegible (Hashes)
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Muestra la pantalla de inicio de sesión.
     * Spring Security se encargará automáticamente de procesar los datos ingresados aquí.
     */
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Llama al archivo login.html
    }

    /**
     * Muestra la pantalla de registro con el formulario para crear una cuenta nueva.
     */
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro"; // Llama al archivo registro.html
    }

    /**
     * Recibe y procesa todos los datos que el cliente escribió en el formulario de registro.
     * Crea la cuenta, encripta la contraseña y guarda todo en la base de datos.
     */
    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String username,
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam String nombreCompleto,
                                   @RequestParam String telefono, 
                                   @RequestParam java.time.LocalDate fechaNacimiento,
                                   Model model) {
                                       
        // 1. VALIDACIÓN: Evitamos que dos personas intenten registrarse con el mismo nombre de usuario
        if (usuarioRepository.findByUsername(username).isPresent()) {
            // Si el nombre ya existe, recargamos la página de registro y mostramos un mensaje de error
            model.addAttribute("error", "Ese nombre de usuario ya está ocupado.");
            return "registro";
        }

        // 2. CREACIÓN: Armamos el perfil del nuevo cliente con los datos recibidos
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setEmail(email);
        
        // ¡Importante! Encriptamos la contraseña antes de guardarla por seguridad
        nuevoUsuario.setPassword(passwordEncoder.encode(password));
        
        nuevoUsuario.setNombreCompleto(nombreCompleto);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setFechaNacimiento(fechaNacimiento);
        
        // 3. GUARDADO: Metemos al nuevo usuario a la base de datos
        usuarioRepository.save(nuevoUsuario);

        // 4. REDIRECCIÓN: Si todo salió bien, lo mandamos a la pantalla de login 
        // y le pasamos un aviso (?registrado=true) para mostrarle un mensaje de éxito
        return "redirect:/login?registrado=true";
    }
}