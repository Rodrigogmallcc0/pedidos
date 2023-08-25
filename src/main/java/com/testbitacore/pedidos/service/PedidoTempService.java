package com.testbitacore.pedidos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.testbitacore.pedidos.model.DetallePedido;

@Service
public class PedidoTempService {

    private final List<DetallePedido> detalleTemporal = new ArrayList<>();

      public void agregarDetalleTemporal(DetallePedido detalle) {
      detalleTemporal.add(detalle);
      }
      public List<DetallePedido> getDetallesTemporales(){
         return detalleTemporal;
      }
      public void limpiar(){
         detalleTemporal.clear();
      }
}
