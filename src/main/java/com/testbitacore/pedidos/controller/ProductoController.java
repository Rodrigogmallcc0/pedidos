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
import com.testbitacore.pedidos.Exception.ProductoException.ProductoNotFoundException;
import com.testbitacore.pedidos.model.Producto;
import com.testbitacore.pedidos.service.ProductoService;

@RestController
@RequestMapping("/api/v1/producto")
public class ProductoController {

    @Autowired 
    private ProductoService productoService;

    @PostMapping(value="/buscarP/{nombre}", produces = "application/json")
    public ResponseEntity<?> buscarP(@PathVariable String nombre){
        try {
            List<Producto> productos = productoService.buscarP(nombre);
            return ResponseEntity.ok(productos);
        } catch (ProductoNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage()));
        }
    }    
}
