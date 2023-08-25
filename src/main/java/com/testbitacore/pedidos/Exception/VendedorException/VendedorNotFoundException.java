package com.testbitacore.pedidos.Exception.VendedorException;

public class VendedorNotFoundException extends RuntimeException {
    public VendedorNotFoundException(String mensaje){
        super(mensaje);
}
}
