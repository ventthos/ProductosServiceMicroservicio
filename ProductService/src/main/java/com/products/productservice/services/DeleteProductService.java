package com.products.productservice.services;

import com.products.productservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Slf4j
public class DeleteProductService implements ServiceHandler<String, Boolean>{
    private final ProductoRepository productoRepository;
    private final RestTemplate restTemplate;

    public Boolean execute(String id) {
        log.info("Petición recibida para eliminar el producto con ID: {}", id);

        if (!productoRepository.existsById(id)) {
            log.warn("Intento de eliminación fallido: El producto con ID {} no existe.", id);
            throw new NoSuchElementException("El producto con ID " + id + " no existe.");
        }

        // Validar si el producto está en una orden
        try {
            Boolean existsInOrder = restTemplate.getForObject("http://ordenes-service/ordenes/exists-product/" + id, Boolean.class);
            if (existsInOrder != null && existsInOrder) {
                log.warn("No se puede eliminar el producto {}: está asociado a una o más órdenes.", id);
                throw new IllegalStateException("No se puede eliminar el producto porque está asociado a una orden.");
            }
        } catch (Exception e) {
            log.error("Error al consultar OrdenService para el producto {}: {}", id, e.getMessage());
            // En caso de error de comunicación, podrías decidir si permitir o no la eliminación.
            // Por seguridad, lanzamos excepción.
            throw new RuntimeException("Error al validar asociaciones del producto con órdenes", e);
        }

        try {
            productoRepository.deleteById(id);
            log.info("Producto con ID {} eliminado exitosamente.", id);
            return true;
        } catch (Exception e) {
            log.error("Error técnico al eliminar el producto {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error interno al eliminar", e);
        }
    }
}