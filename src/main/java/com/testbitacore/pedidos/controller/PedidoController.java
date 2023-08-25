package com.testbitacore.pedidos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testbitacore.pedidos.Exception.ErrorResponse;
import com.testbitacore.pedidos.model.DetallePedido;
import com.testbitacore.pedidos.model.Pedido;
import com.testbitacore.pedidos.service.PedidoService;
import com.testbitacore.pedidos.service.PedidoTempService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/pedido")
public class PedidoController {
    @Autowired 
    private PedidoService pedidoService;
    @Autowired
    private PedidoTempService pedidoTempService;

    @PostMapping("temporal")
    public ResponseEntity<String> temporal(@RequestBody DetallePedido detalle){
        pedidoTempService.agregarDetalleTemporal(detalle);
        return ResponseEntity.ok("Detalle agregado temporalment");
    }

    @PostMapping("/pedido")
    public ResponseEntity<String> crearPedido(@RequestBody Pedido pedido, @RequestBody List<DetallePedido> detallePedidos){
        try {
            Pedido nuevoPedido= pedidoService.crearPedido(pedido, detallePedidos);
            return ResponseEntity.ok("Pedido creado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al crear el pedido");
        }       
    }

    @PostMapping("/{idPedido}/agregar-producto/{idProducto}")
    public ResponseEntity<?> nuevoPedido(@PathVariable Long idPedido, @PathVariable Long idProducto, @RequestParam int cantidad) {
        try {
            Pedido pedidoActualizado = pedidoService.agregarProductoAlPedido(idPedido, idProducto, cantidad);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno en el servidor."));
        }
    }
}
