package com.products.productservice.controller;

import com.products.productservice.dto.CreateProductDto;
import com.products.productservice.dto.PutProductDto;
import com.products.productservice.models.Producto;
import com.products.productservice.response.GeneralResponse;
import com.products.productservice.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final GetProductoService getProductoService;
    private final CreateProductService createProductService;
    private final UpdateProductService updateProductService;
    private final GetProductByIdService getProductByIdService;
    private final DeleteProductService deleteProductService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<Producto>>> getProductos() {

        List<Producto> productos = getProductoService.execute(null);

        return ResponseEntity.ok(
                GeneralResponse.<List<Producto>>builder()
                        .status("SUCCESS")
                        .message("Productos obtenidos correctamente")
                        .data(productos)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<Producto>> getProductoById(@PathVariable String id) {
        Producto producto = getProductByIdService.execute(id);

        return ResponseEntity.ok(
                GeneralResponse.<Producto>builder()
                        .status("SUCCESS")
                        .message("Producto encontrado")
                        .data(producto)
                        .build()
        );
    }

    // --- CREAR (POST) ---
    @PostMapping
    public ResponseEntity<GeneralResponse<Producto>> createProducto(@RequestBody CreateProductDto dto) {

        Producto nuevoProducto = createProductService.execute(dto);

        return ResponseEntity.status(HttpStatus.CREATED) // HTTP 201
                .body(
                        GeneralResponse.<Producto>builder()
                                .status("SUCCESS")
                                .message("Producto creado con éxito")
                                .data(nuevoProducto)
                                .build()
                );
    }

    // --- ACTUALIZAR (PUT) ---
    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<Producto>> updateProducto(
            @PathVariable String id,
            @RequestBody PutProductDto dto) {

        Producto actualizado = updateProductService.execute(id, dto);

        return ResponseEntity.ok(
                GeneralResponse.<Producto>builder()
                        .status("SUCCESS")
                        .message("Producto actualizado correctamente")
                        .data(actualizado)
                        .build()
        );
    }

    // --- ELIMINAR (DELETE) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse<Void>> deleteProducto(@PathVariable String id) {
        deleteProductService.execute(id);

        return ResponseEntity.ok(
                GeneralResponse.<Void>builder()
                        .status("SUCCESS")
                        .message("Producto eliminado correctamente")
                        .data(null)
                        .build()
        );
    }

}
