package com.testbitacore.pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testbitacore.pedidos.Exception.VendedorException.VendedorNotFoundException;
import com.testbitacore.pedidos.model.Vendedor;
import com.testbitacore.pedidos.repository.IVendedorRepository;

@Service
public class VendedorServicio {
    
    @Autowired
    private IVendedorRepository vendedorRepository;

    @Transactional
    public List<Vendedor> buscarV(String nombres){
        List<Vendedor> vendedor = vendedorRepository.buscarVendedoresPorNombre(nombres);
        if(vendedor.isEmpty()){
            throw new VendedorNotFoundException("No se encontraron vendedores con el nombre: "+nombres);
        }
        return  vendedor;
    }
}
