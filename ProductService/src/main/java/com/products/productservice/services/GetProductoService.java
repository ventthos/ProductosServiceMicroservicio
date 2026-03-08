package com.products.productservice.services;

import com.products.productservice.models.Producto;
import com.products.productservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class GetProductoService implements ServiceHandler<Void, List<Producto>>{
    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> execute(Void data) {
        log.info("Obteniendo todos productos.");

        try {
            List<Producto> productos = productoRepository.findAll();

            log.info("Consulta exitosa: se recuperaron {} productos de la base de datos.", productos.size());

            if (productos.isEmpty()) {
                log.warn("La consulta se completó correctamente, pero la colección de productos está vacía.");
            }

            return productos;

        } catch (Exception e) {
            log.error("Fallo al intentar obtener la lista de productos: {}", e.getMessage(), e);
            throw new RuntimeException("Error al consultar el catálogo de productos", e);
        }
    }
}