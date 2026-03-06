package com.products.productservice.services;

import com.products.productservice.models.Producto;
import com.products.productservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetProductByIdService implements ServiceHandler<String, Producto>{
    private final ProductoRepository productoRepository;

    public Producto execute(String id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el producto con el ID: " + id));
    }
}