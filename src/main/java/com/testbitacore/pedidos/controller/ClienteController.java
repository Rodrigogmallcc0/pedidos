package com.testbitacore.pedidos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testbitacore.pedidos.Exception.ErrorResponse;
import com.testbitacore.pedidos.Exception.ClienteException.ClienteNotFoundException;
import com.testbitacore.pedidos.model.Cliente;
import com.testbitacore.pedidos.service.ClienteService;

@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;


    @PostMapping("/agregar")
    public void agregarCliente (@RequestBody Cliente c){
        clienteService.agregarCliente(c.getRuc(),
        c.getRazon_social(),
        c.getEmail(),
        c.getTelefono(),
        c.getDireccion()
        );
    }

    
    @PostMapping("/buscar/{razon_social}")
    public ResponseEntity<?> buscarC(@PathVariable String razon_social){
        try {
            List<Cliente> clientes = clienteService.buscarC(razon_social);
            return ResponseEntity.ok(clientes);
        } catch (ClienteNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage()));
        }
    }
    
    @GetMapping("/listRazon")
    public List<String> listarRazon(){
        return clienteService.listarRazon();
    }
    
}
