package com.products.productservice.services;

import com.products.productservice.dto.CreateProductDto;
import com.products.productservice.models.Producto;
import com.products.productservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CreateProductService implements ServiceHandler<CreateProductDto, Producto>{
    private final ProductoRepository productoRepository;

    @Override
    public Producto execute(CreateProductDto data) {
        Producto producto = Producto.builder()
                .name(data.getName())
                .description(data.getDescription())
                .price(data.getPrice())
                .quantity(data.getQuantity())
                .imageUrl(data.getImageUrl())
                .supplier(data.getSupplier())
                .build();

        productoRepository.save(producto);
        return  producto;
    }
}
