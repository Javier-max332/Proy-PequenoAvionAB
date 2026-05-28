package com.pequenoavion.pequenoavionAB.controller;

import com.pequenoavion.pequenoavionAB.model.Direccion;
import com.pequenoavion.pequenoavionAB.model.Factura;
import com.pequenoavion.pequenoavionAB.model.ItemCarrito;
import com.pequenoavion.pequenoavionAB.model.Pago;
import com.pequenoavion.pequenoavionAB.model.Producto;
import com.pequenoavion.pequenoavionAB.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import java.math.BigDecimal;
import com.pequenoavion.pequenoavionAB.model.Pedido;
import com.pequenoavion.pequenoavionAB.model.PedidoDetalle;
import com.pequenoavion.pequenoavionAB.model.PedidoDetalleId;
import com.pequenoavion.pequenoavionAB.model.Usuario;
import com.pequenoavion.pequenoavionAB.repository.DireccionRepository;
import com.pequenoavion.pequenoavionAB.repository.FacturaRepository;
import com.pequenoavion.pequenoavionAB.repository.PagoRepository;
import com.pequenoavion.pequenoavionAB.repository.PedidoRepository;
import com.pequenoavion.pequenoavionAB.repository.PedidoDetalleRepository;
import com.pequenoavion.pequenoavionAB.repository.UsuarioRepository;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador principal para manejar el Carrito de Compras y el proceso de Pago (Checkout).
 * Funciona guardando los productos temporalmente en la "Sesión" del navegador
 * hasta que el cliente decide confirmar su compra.
 */
@Controller
public class CarritoController {
    
    // Inyección de dependencias para acceder a la base de datos
    @Autowired private FacturaRepository facturaRepository;
    @Autowired private PagoRepository pagoRepository;
    @Autowired private DireccionRepository direccionRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private PedidoDetalleRepository pedidoDetalleRepository;

    /**
     * Agrega un producto al carrito recargando la página.
     * Busca el producto y lo guarda en la memoria (sesión) del usuario.
     */
    @GetMapping("/carrito/agregar/{id}")
    public String agregarAlCarrito(@PathVariable("id") Integer id, HttpSession session) {
        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto != null) {
            // Recuperamos el carrito actual o creamos uno nuevo si está vacío
            List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new ArrayList<>();
            }

            // Verificamos si ya lo teníamos agregado para solo subir la cantidad
            boolean existe = false;
            for (ItemCarrito item : carrito) {
                if (item.getProductoId().equals(producto.getId())) {
                    item.setCantidad(item.getCantidad() + 1);
                    existe = true;
                    break;
                }
            }

            // Si es un producto nuevo en el carrito, lo agregamos con cantidad 1
            if (!existe) {
                ItemCarrito nuevoItem = new ItemCarrito(producto.getId(), producto.getNombre(), 1, producto.getPrecio(), producto.getImagenUrl());
                carrito.add(nuevoItem);
            }

            // Actualizamos la sesión
            session.setAttribute("carrito", carrito);
        }

        return "redirect:/";
    }

    /**
     * Muestra la pantalla del carrito con todos los productos guardados y sus totales.
     */
    @GetMapping("/carrito")
    public String verCarrito(HttpSession session, Model model, Principal principal) {
        
        // EL FIX DE LA BARRA DE NAVEGACIÓN: Verificar si hay sesión iniciada
        boolean logueado = (principal != null);
        model.addAttribute("logueado", logueado);
        
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        // Sumamos el dinero total de todo el carrito
        BigDecimal total = BigDecimal.ZERO;
        int totalItems = 0;
        
        for (ItemCarrito item : carrito) {
            total = total.add(item.getSubtotal());
            totalItems += item.getCantidad();
        }

        // Enviamos los datos al HTML
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        model.addAttribute("cantidadCarrito", totalItems);

        return "carrito";
    }

    /**
     * Agrega un producto al carrito de forma invisible (con AJAX/fetch), 
     * validando que haya existencias (stock) disponibles en la base de datos.
     */
    @PostMapping("/carrito/agregar-ajax/{id}")
    @ResponseBody 
    public int agregarAlCarritoAjax(@PathVariable("id") Integer id, HttpSession session) {
        Producto producto = productoRepository.findById(id).orElse(null);
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        if (producto != null) {
            // Validamos que la tienda no se haya quedado sin el producto
            if (producto.getStock() <= 0) {
                return calcularTotalArticulos(carrito);
            }

            boolean existe = false;
            for (ItemCarrito item : carrito) {
                if (item.getProductoId().equals(producto.getId())) {
                    // No permitimos agregar más piezas de las que hay en stock
                    if (item.getCantidad() < producto.getStock()) {
                        item.setCantidad(item.getCantidad() + 1);
                    }
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                carrito.add(new ItemCarrito(producto.getId(), producto.getNombre(), 1, producto.getPrecio(), producto.getImagenUrl()));
            }
            session.setAttribute("carrito", carrito);
        }

        return calcularTotalArticulos(carrito);
    }

    /**
     * Muestra la pantalla de confirmación de compra (Checkout).
     * Verifica que el usuario haya iniciado sesión y tenga una dirección guardada.
     */
    @GetMapping("/checkout")
    public String mostrarCheckout(HttpSession session, Model model, Authentication auth, Principal principal) {
        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
        if (usuario == null) return "redirect:/login";
        
        // 1. EL FIX DE LA BARRA DE NAVEGACIÓN
        boolean logueado = (principal != null);
        model.addAttribute("logueado", logueado);

        // Traemos las direcciones del usuario para saber a dónde enviar el paquete
        List<Direccion> misDirecciones = direccionRepository.findByUsuarioId(usuario.getId());
        if (misDirecciones == null || misDirecciones.isEmpty()) {
            return "redirect:/direcciones?alertaCheckout=true";
        }

        // Revisamos si el usuario eligió una dirección específica o usamos la primera por defecto
        Integer idSeleccionada = (Integer) session.getAttribute("direccionSeleccionadaId");
        Direccion direccionEnvio = misDirecciones.stream()
                .filter(d -> idSeleccionada != null && d.getId().equals(idSeleccionada))
                .findFirst()
                .orElse(misDirecciones.get(0));

        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) return "redirect:/catalogo";

        double subtotal = carrito.stream().mapToDouble(i -> i.getSubtotal().doubleValue()).sum();

        model.addAttribute("direccion", direccionEnvio); 
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("total", subtotal); 
        
        return "checkout";
    }

    /**
     * Procesa la compra final: crea el pedido, descuenta el stock de los productos,
     * genera el registro de pago y vacía el carrito.
     */
    @PostMapping("/confirmar-pedido")
    public String confirmarPedido(@RequestParam String metodoPago, HttpSession session, Authentication auth, Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
        if (usuario == null) return "redirect:/login";
        // 1. EL FIX DE LA BARRA DE NAVEGACIÓN
        boolean logueado = (principal != null);
        model.addAttribute("logueado", logueado);

        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) return "redirect:/catalogo";

        // Obtenemos la dirección final de envío
        List<Direccion> misDirecciones = direccionRepository.findByUsuarioId(usuario.getId());
        Integer idSeleccionada = (Integer) session.getAttribute("direccionSeleccionadaId");
        Direccion direccionEnvio = misDirecciones.stream()
                .filter(d -> idSeleccionada != null && d.getId().equals(idSeleccionada))
                .findFirst().orElse(misDirecciones.get(0));

        double total = carrito.stream().mapToDouble(i -> i.getSubtotal().doubleValue()).sum();

        // 1. Creamos y guardamos el Pedido general
        Pedido pedido = new Pedido();
        String numPedidoGenerado = "PA-" + System.currentTimeMillis();
        pedido.setNumPedido(numPedidoGenerado);
        pedido.setUsuarioId(usuario.getId());
        pedido.setDireccionId(direccionEnvio.getId());
        pedido.setFechaCompra(LocalDateTime.now());
        pedido.setFechaEntregaEstimada(LocalDate.now().plusDays(14 + (int)(Math.random() * 28))); // Entrega en 14-42 días
        pedido.setTotal(BigDecimal.valueOf(total));
        pedido.setEstadoEnvio("PENDIENTE"); 
        
        pedido = pedidoRepository.save(pedido);

        // 2. Guardamos los detalles (productos individuales) y descontamos stock
        for (ItemCarrito item : carrito) {
            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setId(new PedidoDetalleId(pedido.getId(), item.getProductoId()));
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecioUnitario()); 
            detalle.setImporte(item.getSubtotal());
            pedidoDetalleRepository.save(detalle);

            // Descontamos del inventario
            Producto productoDB = productoRepository.findById(item.getProductoId()).orElse(null);
            if (productoDB != null) {
                int nuevoStock = Math.max(0, productoDB.getStock() - item.getCantidad());
                productoDB.setStock(nuevoStock);
                productoRepository.save(productoDB);
            }
        }

        // 3. Creamos el registro del Pago
        Pago pago = new Pago();
        pago.setPedidoId(pedido.getId());
        pago.setMonto(BigDecimal.valueOf(total));
        pago.setMetodoPago(metodoPago); 
        pago.setEstadoPago("PENDIENTE");
        pagoRepository.save(pago);

        // 4. Limpiamos el carrito y mandamos a la vista de éxito
        session.removeAttribute("carrito");
        return "redirect:/mis-pedidos?ordenExito=" + numPedidoGenerado;
    }

    /**
     * Permite sumar, restar o eliminar productos desde la pantalla del carrito usando AJAX.
     */
    @PostMapping("/carrito/modificar/{id}/{accion}")
    @ResponseBody
    public Map<String, Object> modificarCarrito(@PathVariable("id") Integer id, @PathVariable("accion") String accion, HttpSession session) {
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        Map<String, Object> respuesta = new HashMap<>();

        if (carrito != null) {
            carrito.removeIf(item -> {
                if (item.getProductoId().equals(id)) {
                    if ("sumar".equals(accion)) {
                        Producto p = productoRepository.findById(id).orElse(null);
                        if (p != null && item.getCantidad() < p.getStock()) {
                            item.setCantidad(item.getCantidad() + 1);
                        }
                    } else if ("restar".equals(accion) && item.getCantidad() > 1) {
                        item.setCantidad(item.getCantidad() - 1);
                    } else if ("eliminar".equals(accion)) {
                        return true; // Se borra de la lista
                    }
                }
                return false;
            });
        }

        session.setAttribute("carrito", carrito);
        respuesta.put("status", "ok");
        return respuesta;
    }

    /**
     * Borra absolutamente todo lo que hay en el carrito.
     */
    @GetMapping("/carrito/vaciar")
    public String vaciarCarrito(HttpSession session) {
        session.removeAttribute("carrito");
        return "redirect:/carrito";
    }

    /**
     * Simula que el cliente ya pagó, cambiando el estado del pedido y del pago en la BD.
     */
    @PostMapping("/pagar-pedido/{id}")
    public String simularPago(@PathVariable Integer id) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        Pago pago = pagoRepository.findByPedidoId(id).orElse(null);

        if (pedido != null && pago != null) {
            pedido.setEstadoEnvio("PROCESANDO_ENVIO");
            pago.setEstadoPago("COMPLETADO");
            
            pedidoRepository.save(pedido);
            pagoRepository.save(pago);
        }
        
        return "redirect:/mis-pedidos";
    }
    
    /**
     * Muestra o genera por primera vez la factura de un pedido.
     */
    @GetMapping("/factura/{pedidoId}")
    public String verFactura(@PathVariable Integer pedidoId, Model model) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
        Pago pago = pagoRepository.findByPedidoId(pedidoId).orElse(null);
        
        if (pedido == null || pago == null) return "redirect:/mis-pedidos";

        Usuario usuario = usuarioRepository.findById(pedido.getUsuarioId()).orElse(new Usuario());
        Direccion direccion = direccionRepository.findById(pedido.getDireccionId()).orElse(new Direccion());

        // Si la factura no existe, la creamos al momento
        Factura factura = facturaRepository.findByPagoId(pago.getId()).orElse(null);
        if (factura == null) {
            factura = new Factura();
            factura.setPagoId(pago.getId());
            factura.setRfc("XAXX010101000"); // RFC Genérico
            factura.setRazonSocial(usuario.getNombreCompleto() != null ? usuario.getNombreCompleto() : usuario.getUsername());
            factura.setFechaEmision(LocalDateTime.now());
            factura = facturaRepository.save(factura); 
        }

        // Preparamos la lista de productos comprados para que el HTML los lea fácil
        List<PedidoDetalle> detallesBD = pedidoDetalleRepository.findByIdPedidoId(pedido.getId());
        List<Map<String, Object>> listaProductos = new ArrayList<>();
        
        for (PedidoDetalle det : detallesBD) {
            Producto p = productoRepository.findById(det.getId().getProductoId()).orElse(new Producto());
            Map<String, Object> item = new HashMap<>();
            item.put("nombre", p.getNombre());
            item.put("cantidad", det.getCantidad());
            item.put("precio", det.getPrecioUnitario());
            item.put("importe", det.getImporte());
            listaProductos.add(item);
        }

        // Mandamos las variables a la vista
        model.addAttribute("pedido", pedido);
        model.addAttribute("pago", pago);
        model.addAttribute("factura", factura);
        model.addAttribute("usuario", usuario);
        model.addAttribute("direccion", direccion);
        model.addAttribute("listaProductos", listaProductos);

        return "factura";
    }

    // --- Método auxiliar privado ---
    private int calcularTotalArticulos(List<ItemCarrito> carrito) {
        int total = 0;
        for (ItemCarrito item : carrito) {
            total += item.getCantidad();
        }
        return total;
    }
}