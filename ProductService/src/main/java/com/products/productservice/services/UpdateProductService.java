package com.products.productservice.services;

import com.products.productservice.dto.PutProductDto;
import com.products.productservice.models.Producto;
import com.products.productservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Slf4j
public class UpdateProductService {
    private final ProductoRepository productoRepository;

    public Producto execute(String id, PutProductDto data) {
        log.info("Petición de actualización para el producto ID: {}", id);

        if (!productoRepository.existsById(id)) {
            log.warn("Fallo de actualización: El ID {} no existe.", id);
            throw new NoSuchElementException("No se puede actualizar. El producto con ID: " + id + " no existe.");
        }

        try {
            Producto producto = Producto.builder()
                    .id(id)
                    .name(data.getName())
                    .description(data.getDescription())
                    .price(data.getPrice())
                    .quantity(data.getQuantity())
                    .imageUrl(data.getImageUrl())
                    .supplier(data.getSupplier())
                    .build();

            Producto updatedProduct = productoRepository.save(producto);

            log.info("Producto con ID {} actualizado exitosamente.", id);
            return updatedProduct;

        } catch (Exception e) {
            log.error("Error crítico al actualizar el producto {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error técnico durante la actualización del producto", e);
        }
    }
}