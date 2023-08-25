package com.testbitacore.pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.testbitacore.pedidos.model.Producto;

public interface IProductoRepository extends JpaRepository<Producto, Long>{

    @Procedure(name = "BuscarProductosPorNombre")
    List<Producto> buscarProductosPorNombre(String nombre);

    
    
}
