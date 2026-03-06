package com.products.productservice.services;

import com.products.productservice.models.Producto;
import com.products.productservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetProductoService implements ServiceHandler<Void, List<Producto>>{
    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> execute(Void data) {
        return productoRepository.findAll();
    }
}
