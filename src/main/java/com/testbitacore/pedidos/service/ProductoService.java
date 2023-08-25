package com.testbitacore.pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testbitacore.pedidos.Exception.ProductoException.ProductoNotFoundException;
import com.testbitacore.pedidos.model.Producto;
import com.testbitacore.pedidos.repository.IProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoService {

    @Autowired
    private IProductoRepository productoRepository;

    @Transactional
    public List<Producto> buscarP(String nombre){
        List<Producto> productos = productoRepository.buscarProductosPorNombre(nombre);
        if (productos.isEmpty()){
            throw new ProductoNotFoundException("No se encontraron productos con el nombre: " + nombre);
        }
        return productos;
    }

    

}
