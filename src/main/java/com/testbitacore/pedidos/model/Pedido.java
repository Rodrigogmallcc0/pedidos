package com.testbitacore.pedidos.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Pedido {
    @Id
    private int id;
    private String codigo_pedido;
    private Date fecha_emision;
    private int cliente_id;
    private String razon_social_cliente;
    private int vendedor_id;
    private String forma_pago;
    private int dias_pago;
    private String direccion_entrega;
    private List<DetallePedido> detalles = new ArrayList<>();
    private double total;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCodigo_pedido() {
        return codigo_pedido;
    }
    public void setCodigo_pedido(String codigo_pedido) {
        this.codigo_pedido = codigo_pedido;
    }
    public Date getFecha_emision() {
        return fecha_emision;
    }
    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }
    public int getCliente_id() {
        return cliente_id;
    }
    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }
    public String getRazon_social_cliente() {
        return razon_social_cliente;
    }
    public void setRazon_social_cliente(String razon_social_cliente) {
        this.razon_social_cliente = razon_social_cliente;
    }
    public int getVendedor_id() {
        return vendedor_id;
    }
    public void setVendedor_id(int vendedor_id) {
        this.vendedor_id = vendedor_id;
    }
    public String getForma_pago() {
        return forma_pago;
    }
    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }
    public int getDias_pago() {
        return dias_pago;
    }
    public void setDias_pago(int dias_pago) {
        this.dias_pago = dias_pago;
    }
    public String getDireccion_entrega() {
        return direccion_entrega;
    }
    public void setDireccion_entrega(String direccion_entrega) {
        this.direccion_entrega = direccion_entrega;
    }
    public List<DetallePedido> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    
}
