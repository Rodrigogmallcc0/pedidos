package com.testbitacore.pedidos.Exception.ClienteException;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String mensaje){
        super(mensaje);
    }
}
