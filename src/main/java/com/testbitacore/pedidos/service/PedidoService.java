package com.testbitacore.pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testbitacore.pedidos.model.DetallePedido;
import com.testbitacore.pedidos.model.Pedido;
import com.testbitacore.pedidos.model.Producto;
import com.testbitacore.pedidos.repository.IDetallePedidoRepository;
import com.testbitacore.pedidos.repository.IPedidoRepository;
import com.testbitacore.pedidos.repository.IProductoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
@Service

public class PedidoService {

      @Autowired
      private IPedidoRepository pedidoRepository;
      @Autowired
      private IDetallePedidoRepository detallePedidoRepository;
      @Autowired 
      private IProductoRepository productoRepository; 

     
      @Transactional
      public Pedido agregarProductoAlPedido(Long idPedido, Long idProducto, int cantidad) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado."));
        
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado."));

        DetallePedido nuevoDetalle = new DetallePedido();
        nuevoDetalle.setProducto(producto);
        nuevoDetalle.setCantidad(cantidad);
        nuevoDetalle.setSubtotal(producto.getPrecio() * cantidad);

        pedido.getDetalles().add(nuevoDetalle);
        pedido.setTotal(pedido.getTotal() + nuevoDetalle.getSubtotal());

        return pedidoRepository.save(pedido);
      }

        public Pedido crearPedido(Pedido pedido, List<DetallePedido> detalles){
        pedido = pedidoRepository.save(pedido);
        for (DetallePedido detalle : detalles){
            detalle.setPedido(pedido);
            detallePedidoRepository.save(detalle);
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock()- detalle.getCantidad());
            productoRepository.save(producto);
        }
        return pedido;
    }
     


}
