package com.pequenoavion.pequenoavionAB.controller;

import com.pequenoavion.pequenoavionAB.model.Pago;
import com.pequenoavion.pequenoavionAB.model.Pedido;
import com.pequenoavion.pequenoavionAB.model.PedidoDetalle;
import com.pequenoavion.pequenoavionAB.model.Producto;
import com.pequenoavion.pequenoavionAB.repository.PagoRepository;
import com.pequenoavion.pequenoavionAB.repository.PedidoDetalleRepository;
import com.pequenoavion.pequenoavionAB.repository.PedidoRepository;
import com.pequenoavion.pequenoavionAB.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Tarea en Segundo Plano (OrdenExpiracionTask)
 * Funciona como un "robot" que está vigilando la tienda silenciosamente.
 * Su único trabajo es buscar compras que la gente apartó pero nunca pagó,
 * cancelarlas, y regresar esos productos a los estantes (inventario)
 * para que alguien más pueda comprarlos.
 */
@Component // Le dice a Spring que este es un componente del sistema que se crea automáticamente
public class OrdenExpiracionTask {

    // --- INYECCIÓN DE DEPENDENCIAS ---
    // Repositorios necesarios para leer y modificar las tablas de la base de datos
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private PagoRepository pagoRepository;
    @Autowired private PedidoDetalleRepository pedidoDetalleRepository;
    @Autowired private ProductoRepository productoRepository;

    /**
     * Método principal que realiza la limpieza de pedidos expirados.
     * 
     * @Scheduled(fixedDelay = 60000): Le dice al sistema que ejecute este código 
     * automáticamente cada 60,000 milisegundos (es decir, cada 60 segundos).
     * 
     * @Transactional: Es una red de seguridad. Garantiza que si ocurre un error a la mitad 
     * del proceso (por ejemplo, se cancela el pedido pero falla al regresar el stock), 
     * se deshagan todos los cambios para no dejar datos corruptos.
     */
    @Scheduled(fixedDelay = 60000) 
    @Transactional 
    public void verificarYCancelarPedidosExpirados() {
        
        // 1. REVISIÓN: Buscamos todos los pedidos que están atorados esperando el pago ("PENDIENTE")
        List<Pedido> pedidosPendientes = pedidoRepository.findByEstadoEnvio("PENDIENTE");
        LocalDateTime ahora = LocalDateTime.now();

        // Revisamos uno por uno los pedidos pendientes encontrados
        for (Pedido pedido : pedidosPendientes) {
            
            // REGLA DE LA TIENDA: Damos exactamente 5 minutos para que el cliente pague su carrito.
            LocalDateTime horaLimite = pedido.getFechaCompra().plusMinutes(5);

            // 2. VERIFICACIÓN: Preguntamos, ¿la hora actual ya superó el tiempo límite que le dimos?
            if (ahora.isAfter(horaLimite)) {
                // --- INICIA EL PROTOCOLO DE CANCELACIÓN ---

                // ACCIÓN A: Cambiar el estado del Pedido a "CANCELADO"
                pedido.setEstadoEnvio("CANCELADO");
                pedidoRepository.save(pedido);

                // ACCIÓN B: Buscar el registro del dinero (Pago) y también marcarlo como "CANCELADO"
                Pago pago = pagoRepository.findByPedidoId(pedido.getId()).orElse(null);
                if (pago != null) {
                    pago.setEstadoPago("CANCELADO");
                    pagoRepository.save(pago);
                }

                // ACCIÓN C: DEVOLVER LAS PIEZAS A LOS ESTANTES (RESTOCK)
                // Buscamos la lista de todos los productos que estaban dentro de este pedido cancelado
                List<PedidoDetalle> itemsComprados = pedidoDetalleRepository.findByIdPedidoId(pedido.getId());
                
                for (PedidoDetalle item : itemsComprados) {
                    // Buscamos el producto original en la base de datos
                    Producto productoDB = productoRepository.findById(item.getId().getProductoId()).orElse(null);
                    
                    if (productoDB != null) {
                        // Sumamos el stock que ya tenía la tienda MÁS la cantidad que el cliente había apartado
                        int stockRestaurado = productoDB.getStock() + item.getCantidad();
                        
                        // Guardamos el nuevo inventario total
                        productoDB.setStock(stockRestaurado);
                        productoRepository.save(productoDB);
                    }
                }
            }
        }
    }
}