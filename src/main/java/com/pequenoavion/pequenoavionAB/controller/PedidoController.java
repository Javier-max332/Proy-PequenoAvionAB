package com.pequenoavion.pequenoavionAB.controller;

import com.pequenoavion.pequenoavionAB.model.Pedido;
import com.pequenoavion.pequenoavionAB.model.Usuario;
import com.pequenoavion.pequenoavionAB.repository.PagoRepository;
import com.pequenoavion.pequenoavionAB.repository.PedidoRepository;
import com.pequenoavion.pequenoavionAB.repository.UsuarioRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.security.core.Authentication;

/**
 * Controlador de Pedidos (PedidoController)
 * Su trabajo principal es gestionar el historial de compras.
 * Aquí es donde los clientes pueden entrar a revisar qué han comprado, 
 * el estado de su envío y sus facturas.
 */
@Controller
public class PedidoController {
    
    // --- INYECCIÓN DE DEPENDENCIAS ---
    // Herramientas para buscar información en nuestras tablas de la base de datos
    @Autowired
    private PagoRepository pagoRepository; // (Nota: Lista para usarse en futuros métodos de pago)
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * Muestra la pantalla "Mis Pedidos" con el historial exclusivo del cliente.
     * Protege la privacidad asegurando que cada persona solo vea sus propias compras.
     */
    @GetMapping("/mis-pedidos")
    public String verMisPedidos(Model model, Authentication auth, Principal principal) {
        
        // 1. IDENTIFICACIÓN: Preguntamos a Spring Security cuál es el nombre de usuario de la persona logueada
        String username = auth.getName();
        
        // 1. EL FIX DE LA BARRA DE NAVEGACIÓN
        boolean logueado = (principal != null);
        model.addAttribute("logueado", logueado);
        
        // Buscamos el perfil completo de ese usuario en la base de datos
        Usuario usuarioReal = usuarioRepository.findByUsername(username).orElse(null);

        // Si encontramos al usuario exitosamente
        if (usuarioReal != null) {
            
            // 2. FILTRADO: Buscamos en la tabla de Pedidos ÚNICAMENTE los que tengan el ID de este cliente
            List<Pedido> misPedidos = pedidoRepository.findByUsuarioId(usuarioReal.getId());
            
            // 3. ENVÍO DE DATOS: Mandamos esa lista de pedidos a la página HTML para que los dibuje
            model.addAttribute("listaPedidos", misPedidos);
        }

        // Redirigimos al navegador para que muestre el archivo mis-pedidos.html
        return "mis-pedidos"; 
    }
    
}