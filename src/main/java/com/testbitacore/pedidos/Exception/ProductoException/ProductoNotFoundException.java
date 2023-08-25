package com.testbitacore.pedidos.Exception.ProductoException;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(String mensaje){
        super(mensaje);
    }
}


