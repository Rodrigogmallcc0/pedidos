package com.testbitacore.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testbitacore.pedidos.model.DetallePedido;

public interface IDetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    
}
