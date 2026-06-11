package com.products.productservice.services;

import com.products.productservice.dto.CreateProductDto;
import com.products.productservice.exceptionhandler.RetryScheduledException;
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
    private final ProductoProducer productoProducer;
    @Override
    public Producto execute(CreateProductDto data) {
        log.info("Iniciando creación de producto: {} ", data.getName(), data.getSupplier());

        if (data.getPrice() == null || data.getPrice() < 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor o igual a 0.");
        }
        if (data.getQuantity() < 0) {
            throw new IllegalArgumentException("El stock inicial debe ser mayor o igual a 0.");
        }

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
            if (!data.isFromRetry()) {
                productoProducer.sendToRetry(data);
                throw new RetryScheduledException(
                        "Hubo un error. Se reintentará crear el producto lo más pronto posible"
                );
            }
            throw new RuntimeException("Error en el servicio de persistencia de productos", e);
        }
    }
}
