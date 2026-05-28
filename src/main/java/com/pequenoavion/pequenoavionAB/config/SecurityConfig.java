package com.pequenoavion.pequenoavionAB.config;

import org.springframework.boot.CommandLineRunner;
import com.pequenoavion.pequenoavionAB.model.Usuario;
import com.pequenoavion.pequenoavionAB.repository.UsuarioRepository;
import com.pequenoavion.pequenoavionAB.service.DetallesUsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Configuramos el encriptador de contraseñas (Para no guardarlas en texto plano en la BD)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Conectamos nuestro "truco" de buscar usuarios con Spring Security
    @Bean
    public DaoAuthenticationProvider authenticationProvider(DetallesUsuarioService detallesUsuarioService) {
        
        // Pasamos el servicio directamente dentro de los paréntesis
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(detallesUsuarioService);
        
        // Le seguimos asignando el encriptador de contraseñas
        authProvider.setPasswordEncoder(passwordEncoder());
        
        return authProvider;
    }

    // 3. LAS REGLAS DE ORO: Quién entra a dónde
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Apagamos temporalmente el filtro CSRF para que tu AJAX (fetch) pueda pasar
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                // 1. ZONA PÚBLICA (Visitantes sin cuenta pueden ver esto)
                .requestMatchers("/", "/factura", "/home", "/catalogo", "/configurador", "/registro", "/css/**", "/img/**", "/js/**").permitAll()
                
                // 2. ZONA PRIVADA DE ADMIN (Solo JavierTV / el Jefe puede ver esto)
                .requestMatchers("/nuevo", "/editar/**", "/eliminar/**", "/admin/**").hasRole("ADMIN")
                
                // 3. ZONA PRIVADA DE CLIENTES (Si intentan entrar al carrito o compras, les pedirá iniciar sesión)
                .requestMatchers("/carrito", "/carrito/**", "/mis-pedidos", "/checkout").authenticated()
                
                // 4. Cualquier otra página pedirá login por defecto
                .anyRequest().authenticated()
            )
            // Configuramos nuestro propio formulario de Login
            .formLogin(form -> form
                .loginPage("/login") // Le decimos que nosotros haremos la vista HTML del login
                .defaultSuccessUrl("/", true) // Si entra bien, lo mandamos a la tienda principal
                .permitAll()
            )
            // Configuramos el botón de Cerrar Sesión
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") // Al salir, lo mandamos a la tienda
                .permitAll()
            );

        return http.build();
    }
    
    @Bean
    public CommandLineRunner initAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verificamos si el admin ya existe en la base de datos
            if (usuarioRepository.findByUsername("JavierTorresAdmin1").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("JavierTorresAdmin1");
                admin.setEmail("mt8966902@gmail.com");
                // Encriptamos la contraseña "admin123"
                admin.setPassword(passwordEncoder.encode("RonTres23@admin")); 
                admin.setNombreCompleto("Javier Torres (Administrador)");
                // (Formato: Año, Mes, Día)
                admin.setTelefono("4631234567"); 
                admin.setFechaNacimiento(java.time.LocalDate.of(2000, 1, 15));
                
                usuarioRepository.save(admin);
                System.out.println("Súper Usuario ADMIN creado exitosamente.");
            }
        };
    }
}