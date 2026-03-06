package com.products.productservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class Producto {
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    private int quantity;
    private String imageUrl;
    private String supplier;
}