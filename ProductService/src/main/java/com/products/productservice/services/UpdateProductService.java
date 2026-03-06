package com.products.productservice.services;

import com.products.productservice.dto.PutProductDto;
import com.products.productservice.models.Producto;
import com.products.productservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateProductService {
    private final ProductoRepository productoRepository;

    public Producto execute(String id, PutProductDto data) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("No se puede actualizar. El producto con ID: " + id + " no existe.");
        }

        // 2. Reemplazar todos los campos (Semántica PUT)
        Producto producto = Producto.builder()
                .id(id) // Mantener el mismo ID para que MongoDB sobrescriba el documento
                .name(data.getName())
                .description(data.getDescription())
                .price(data.getPrice())
                .quantity(data.getQuantity())
                .imageUrl(data.getImageUrl())
                .supplier(data.getSupplier())
                .build();

        // 3. Guardar el reemplazo
        return productoRepository.save(producto);
    }
}