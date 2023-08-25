package com.testbitacore.pedidos.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testbitacore.pedidos.model.DetallePedido;
import com.testbitacore.pedidos.repository.IDetallePedidoRepository;
@Service
public class DetalleService {
    @Autowired 
    private IDetallePedidoRepository detallePedidoRepository;

    public ArrayList<DetallePedido> list(){
        return (ArrayList<DetallePedido>) detallePedidoRepository.findAll();
    }
}
