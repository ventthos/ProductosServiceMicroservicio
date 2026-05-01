package com.products.productservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.products.productservice.dto.CreateProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "product_retry_jobs";

    public void sendToRetry(CreateProductDto dto) {
        dto.setFromRetry(true);
        try {
            String message = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send(TOPIC, message);
        } catch (Exception e) {
            throw new RuntimeException("Error enviando a Kafka", e);
        }
    }
}