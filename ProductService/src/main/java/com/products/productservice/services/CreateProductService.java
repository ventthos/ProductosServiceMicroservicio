package com.products.productservice.services;

import com.products.productservice.dto.CreateProductDto;
import com.products.productservice.models.Producto;
import com.products.productservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateProductService implements ServiceHandler<CreateProductDto, Producto>{
    private final ProductoRepository productoRepository;

    @Override
    public Producto execute(CreateProductDto data) {
        log.info("Iniciando creación de producto: {} ", data.getName(), data.getSupplier());

        try {
            Producto producto = Producto.builder()
                    .name(data.getName())
                    .description(data.getDescription())
                    .price(data.getPrice())
                    .quantity(data.getQuantity())
                    .imageUrl(data.getImageUrl())
                    .supplier(data.getSupplier())
                    .build();

            log.debug("Intentando guardar producto en MongoDB...");

            Producto savedProduct = productoRepository.save(producto);

            log.info("Producto guardado exitosamente. ID asignado: {}", savedProduct.getId());

            return savedProduct;

        } catch (Exception e) {
            log.error("Fallo crítico al crear el producto '{}'. Causa: {}", data.getName(), e.getMessage(), e);
            throw new RuntimeException("Error en el servicio de persistencia de productos", e);
        }
    }
}
