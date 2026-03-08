package com.products.productservice.services;

import com.products.productservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Slf4j
public class DeleteProductService implements ServiceHandler<String, Boolean>{
    private final ProductoRepository productoRepository;

    public Boolean execute(String id) {
        log.info("Petición recibida para eliminar el producto con ID: {}", id);

        if (!productoRepository.existsById(id)) {
            log.warn("Intento de eliminación fallido: El producto con ID {} no existe.", id);
            throw new NoSuchElementException("El producto con ID " + id + " no existe.");
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