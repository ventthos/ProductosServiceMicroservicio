package com.products.productservice.services;

public interface ServiceHandler<D,T> {
    T execute(D data);
}
