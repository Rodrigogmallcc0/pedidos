package com.testbitacore.pedidos.Exception;

public class ErrorResponse {

    private String mensaje;

    public ErrorResponse(String mensaje){
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
