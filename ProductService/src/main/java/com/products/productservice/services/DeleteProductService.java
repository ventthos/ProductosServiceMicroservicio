package com.products.productservice.services;

import com.products.productservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteProductService implements ServiceHandler<String, Boolean>{
    private final ProductoRepository productoRepository;

    public Boolean execute(String id) {
        if (!productoRepository.existsById(id)) {
            return false;
        }

        productoRepository.deleteById(id);
        return true;
    }
}