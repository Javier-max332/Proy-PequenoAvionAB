package com.pequenoavion.pequenoavionAB.controller;

import com.pequenoavion.pequenoavionAB.model.Direccion;
import com.pequenoavion.pequenoavionAB.model.Producto;
import com.pequenoavion.pequenoavionAB.repository.CategoriaRepository;
import com.pequenoavion.pequenoavionAB.repository.ProductoRepository;
import com.pequenoavion.pequenoavionAB.repository.ProveedorRepository;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import com.pequenoavion.pequenoavionAB.model.ItemCarrito;
import com.pequenoavion.pequenoavionAB.model.Usuario;
import com.pequenoavion.pequenoavionAB.repository.DireccionRepository;
import com.pequenoavion.pequenoavionAB.repository.PagoRepository;
import com.pequenoavion.pequenoavionAB.repository.PedidoRepository;
import com.pequenoavion.pequenoavionAB.repository.UsuarioRepository;
import java.security.Principal;
import java.util.ArrayList;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador Principal (IndexController)
 * Maneja las vistas principales de la tienda Pequeño Avión, el catálogo, 
 * la gestión de productos (CRUD) y el perfil del usuario (direcciones).
 */
@Controller
public class IndexController {
    
    // --- INYECCIÓN DE DEPENDENCIAS (Repositorios) ---
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private PagoRepository pagoRepository;
    @Autowired private DireccionRepository direccionRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ProductoRepository productoRepository; 
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private ProveedorRepository proveedorRepository;

    // ==========================================================
    // SECCIÓN 1: NAVEGACIÓN PRINCIPAL Y CATÁLOGO
    // ==========================================================

    /**
     * Carga la Landing Page (Portada)
     */
    @GetMapping("/")
    public String paginaPrincipal(Model model, Authentication auth) {
        // Validación visual de seguridad para el Navbar
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            model.addAttribute("logueado", true);
            model.addAttribute("nombreUsuario", auth.getName());
            boolean esAdmin = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().toUpperCase().contains("ADMIN"));
            model.addAttribute("esAdmin", esAdmin);
        } else {
            model.addAttribute("logueado", false);
            model.addAttribute("esAdmin", false);
        }
        return "home";
    }

    /**
     * Carga la vista del Catálogo de Productos
     */
    @GetMapping("/catalogo")
    public String verCatalogo(Model model, HttpSession session, Authentication auth) {
        model.addAttribute("titulo", "Catálogo - Pequeño Avión");
        model.addAttribute("listaProductos", productoRepository.findAll());

        // Seguridad visual
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            model.addAttribute("logueado", true);
            model.addAttribute("nombreUsuario", auth.getName());
            boolean esAdmin = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().toUpperCase().contains("ADMIN"));
            model.addAttribute("esAdmin", esAdmin);
        } else {
            model.addAttribute("logueado", false);
            model.addAttribute("esAdmin", false);
        }

        // Calcula cuántos ítems hay en el carrito para mostrar la burbujita
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        int cantidadTotal = 0;
        if (carrito != null) {
            for (ItemCarrito item : carrito) {
                cantidadTotal += item.getCantidad();
            }
        }
        model.addAttribute("cantidadCarrito", cantidadTotal);

        return "index";
    }

    /**
     * Carga la vista para armar una PC personalizada
     */
    @GetMapping("/configurador")
    public String configuradorPc(Model model, Authentication auth) {
        model.addAttribute("titulo", "Arma tu PC - Pequeño Avión");

        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            model.addAttribute("logueado", true);
            model.addAttribute("nombreUsuario", auth.getName());
        } else {
            model.addAttribute("logueado", false);
        }

        // Filtra los productos por su ID de categoría para mostrar las piezas separadas
        model.addAttribute("procesadores", productoRepository.findByCategoriaId(1));
        model.addAttribute("tarjetasMadre", productoRepository.findByCategoriaId(2));
        model.addAttribute("rams", productoRepository.findByCategoriaId(3));
        model.addAttribute("almacenamientos", productoRepository.findByCategoriaId(4));
        model.addAttribute("graficas", productoRepository.findByCategoriaId(5));
        model.addAttribute("fuentes", productoRepository.findByCategoriaId(6));
        model.addAttribute("gabinetes", productoRepository.findByCategoriaId(7));
        model.addAttribute("enfriamientos", productoRepository.findByCategoriaId(8));

        return "configurador";
    }

    // ==========================================================
    // SECCIÓN 2: ADMINISTRACIÓN DE PRODUCTOS (CRUD)
    // ==========================================================

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("listaCategorias", categoriaRepository.findAll());
        model.addAttribute("listaProveedores", proveedorRepository.findAll());
        return "formulario";
    }

    // 🔥 AQUÍ ESTÁ LA CORRECCIÓN: Cambiamos "/guardar" por "/productos/guardar"
    @PostMapping("/productos/guardar")
    public String guardarProducto(@Valid Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listaCategorias", categoriaRepository.findAll());
            model.addAttribute("listaProveedores", proveedorRepository.findAll());
            return "formulario";
        }
        productoRepository.save(producto);
        // Te cambié esto para que te regrese al catálogo al terminar de guardar
        return "redirect:/catalogo"; 
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Integer id) {
        productoRepository.deleteById(id);
        return "redirect:/catalogo"; // También mejorado para volver al catálogo
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoRepository.findById(id).orElse(null);
        model.addAttribute("producto", producto);
        model.addAttribute("listaCategorias", categoriaRepository.findAll());
        model.addAttribute("listaProveedores", proveedorRepository.findAll());
        return "formulario";
    }

    // ==========================================================
    // SECCIÓN 3: PERFIL DE USUARIO Y DIRECCIONES
    // ==========================================================

    /**
     * Muestra la información personal de la cuenta
     */
    @GetMapping("/perfil")
    public String verPerfil(Model model, Authentication auth, Principal principal) {
        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
        if (usuario == null) {
            return "redirect:/login";
        }
        
        // 1. EL FIX DE LA BARRA DE NAVEGACIÓN
        boolean logueado = (principal != null);
        model.addAttribute("logueado", logueado);
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Mi Cuenta - Pequeño Avión");
        
        boolean esAdmin = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().toUpperCase().contains("ADMIN"));
        model.addAttribute("rolMostrar", esAdmin ? "Administrador" : "Customer");

        return "perfil";
    }
    
    /**
     * Muestra la lista de direcciones del usuario
     */
    @GetMapping("/direcciones")
    public String verDirecciones(Model model, Authentication auth, HttpSession session, Principal principal) {
        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
        if (usuario == null) return "redirect:/login";
        
        // 1. EL FIX DE LA BARRA DE NAVEGACIÓN
        boolean logueado = (principal != null);
        model.addAttribute("logueado", logueado);

        // Obtiene todas las direcciones históricas del usuario
        List<Direccion> misDirecciones = direccionRepository.findByUsuarioId(usuario.getId());
        
        // Verifica si hay una dirección seleccionada en la memoria temporal
        Integer idSeleccionada = (Integer) session.getAttribute("direccionSeleccionadaId");
        
        // Si no hay ninguna seleccionada, pero sí tiene direcciones registradas, selecciona la primera por defecto
        if (idSeleccionada == null && !misDirecciones.isEmpty()) {
            idSeleccionada = misDirecciones.get(0).getId();
            session.setAttribute("direccionSeleccionadaId", idSeleccionada);
        }

        model.addAttribute("listaDirecciones", misDirecciones);
        model.addAttribute("direccionPrincipalId", idSeleccionada);
        model.addAttribute("titulo", "Mis Direcciones - Pequeño Avión");
        
        boolean esAdmin = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().toUpperCase().contains("ADMIN"));
        model.addAttribute("rolMostrar", esAdmin ? "Administrador" : "Customer");

        return "direcciones";
    }

    /**
     * Guarda la selección del Radio Button en la memoria de sesión
     */
    @PostMapping("/direcciones/seleccionar/{idDir}")
    public String seleccionarPrincipal(@PathVariable Integer idDir, HttpSession session) {
        // Almacena la elección sin hacer consultas pesadas a la base de datos
        session.setAttribute("direccionSeleccionadaId", idDir);
        return "redirect:/direcciones";
    }

    /**
     * Registra una nueva dirección en la base de datos
     */
    @PostMapping("/direcciones/guardar")
    public String guardarDireccion(
            @RequestParam String pais, @RequestParam String estado,
            @RequestParam String municipio, @RequestParam String calle,
            @RequestParam String numExterior, @RequestParam(required = false) String numInterior,
            @RequestParam String codigoPostal, Authentication auth, HttpSession session) {
            
        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
        if (usuario != null) {
            // Crea y mapea el objeto Dirección
            Direccion nuevaDir = new Direccion();
            nuevaDir.setUsuarioId(usuario.getId());
            nuevaDir.setPais(pais);
            nuevaDir.setEstado(estado);
            nuevaDir.setMunicipio(municipio);
            nuevaDir.setCalle(calle);
            nuevaDir.setNumExterior(numExterior);
            nuevaDir.setNumInterior(numInterior);
            nuevaDir.setCodigoPostal(codigoPostal);
            
            // Persiste en MySQL
            nuevaDir = direccionRepository.save(nuevaDir); 
            
            // Automáticamente la marca como la dirección activa para la compra actual
            session.setAttribute("direccionSeleccionadaId", nuevaDir.getId());
        }
        return "redirect:/direcciones?exito=true"; 
    }
    
    // ==========================================================
    // SECCIÓN 4: CARRITO Y CONFIGURADOR DE PC
    // ==========================================================

    @PostMapping("/carrito/agregar-ensamble")
    public String agregarEnsamble(
            @RequestParam(required = false) Integer motherboardId,
            @RequestParam(required = false) Integer procesadorId,
            @RequestParam(required = false) Integer ramId,
            @RequestParam(required = false) Integer gpuId,
            @RequestParam(required = false) Integer almacenamientoId,
            @RequestParam(required = false) Integer fuenteId,
            @RequestParam(required = false) Integer gabineteId,
            @RequestParam(required = false) Integer enfriamientoId,
            HttpSession session) {

        // 1. Recuperamos el carrito actual o creamos uno nuevo
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        // 2. Agregamos las piezas seleccionadas
        agregarPiezaAlCarrito(carrito, motherboardId);
        agregarPiezaAlCarrito(carrito, procesadorId);
        agregarPiezaAlCarrito(carrito, ramId);
        agregarPiezaAlCarrito(carrito, gpuId);
        agregarPiezaAlCarrito(carrito, almacenamientoId);
        agregarPiezaAlCarrito(carrito, fuenteId);
        agregarPiezaAlCarrito(carrito, gabineteId);
        agregarPiezaAlCarrito(carrito, enfriamientoId);

        // 3. Guardamos en sesión
        session.setAttribute("carrito", carrito);

        // 4. Redirigimos al carrito
        return "redirect:/carrito"; 
    }

    // Método auxiliar para buscar el producto en BD y meterlo a la lista
    private void agregarPiezaAlCarrito(List<ItemCarrito> carrito, Integer productoId) {
        if (productoId != null) {
            Producto producto = productoRepository.findById(productoId).orElse(null);
            if (producto != null) {
                ItemCarrito nuevoItem = new ItemCarrito();
                nuevoItem.setProductoId(producto.getId());
                // Asegúrate de que los getters coincidan con tu clase Producto y ItemCarrito
                nuevoItem.setNombre(producto.getMarca() + " " + producto.getModelo());
                nuevoItem.setPrecioUnitario(producto.getPrecio());
                nuevoItem.setCantidad(1);
                nuevoItem.setImagenUrl(producto.getImagenUrl());
                nuevoItem.setSubtotal(producto.getPrecio()); 
                
                carrito.add(nuevoItem);
            }
        }
    }
}