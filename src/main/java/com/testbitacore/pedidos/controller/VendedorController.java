package com.testbitacore.pedidos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testbitacore.pedidos.Exception.ErrorResponse;
import com.testbitacore.pedidos.Exception.VendedorException.VendedorNotFoundException;
import com.testbitacore.pedidos.model.Vendedor;
import com.testbitacore.pedidos.service.VendedorServicio;

@RestController
@RequestMapping("/api/v1/vendedor")
public class VendedorController {
    
    @Autowired
    private VendedorServicio vendedorServicio;

    @PostMapping(value = "/buscarV/{nombres}", produces = "application/json")
    public ResponseEntity<?> buscarV(@PathVariable String nombres){
        try {
            List<Vendedor> vendedor= vendedorServicio.buscarV(nombres);
            return ResponseEntity.ok(vendedor);
        } catch (VendedorNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage()));
        } catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno en el servidor."));
        }
    }
}
