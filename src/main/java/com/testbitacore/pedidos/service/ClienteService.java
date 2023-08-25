package com.testbitacore.pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testbitacore.pedidos.Exception.ClienteException.ClienteNotFoundException;
import com.testbitacore.pedidos.model.Cliente;
import com.testbitacore.pedidos.repository.IClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private IClienteRepository clienteRepository;


    public void agregarCliente(String ruc, String razonSocial, String email, String telefono, String direccion) {
        clienteRepository.insertar_cliente(ruc, razonSocial, email, telefono, direccion);
    }
    @Transactional
    public List<Cliente> buscarC(String razon_social){
        List<Cliente> clientes= clienteRepository.BuscarClientesPorRazonSocial(razon_social);
        if(clientes.isEmpty()){
            throw new ClienteNotFoundException("No se encontro el cliente con la razon social: "+razon_social);
        }
        return clientes;
    }

    @Transactional
    public List<String> listarRazon(){
        return clienteRepository.RazonSocial();
    }

  
}
