package com.products.productservice.services;

import com.products.productservice.models.Producto;
import com.products.productservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Slf4j
public class GetProductByIdService implements ServiceHandler<String, Producto>{
    private final ProductoRepository productoRepository;

    @Override
    public Producto execute(String id) {
        log.info("Buscando producto con ID: {}", id);

        return productoRepository.findById(id)
                .map(producto -> {
                    log.info("Producto encontrado: {}", producto.getName());
                    return producto;
                })
                .orElseThrow(() -> {
                    log.warn("Error de búsqueda: No existe un producto con el ID {}", id);
                    return new NoSuchElementException("No se encontró el producto con el ID: " + id);
                });
    }
}