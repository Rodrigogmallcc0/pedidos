package com.testbitacore.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.testbitacore.pedidos.model.Pedido;

@Service
public interface IPedidoRepository extends JpaRepository<Pedido, Long>{
    
}
