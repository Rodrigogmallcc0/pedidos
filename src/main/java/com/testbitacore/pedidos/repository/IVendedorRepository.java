package com.testbitacore.pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.testbitacore.pedidos.model.Vendedor;

public interface IVendedorRepository extends JpaRepository<Vendedor, Long> {
    
    @Procedure(name="")
    void insertarVendedor(
        
    );

    @Procedure(name ="buscarVendedoresPorNombre")
    List<Vendedor> buscarVendedoresPorNombre(String nombres);
    
}
