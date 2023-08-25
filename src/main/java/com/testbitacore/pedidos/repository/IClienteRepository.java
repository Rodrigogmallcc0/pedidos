package com.testbitacore.pedidos.repository;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.testbitacore.pedidos.model.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Long>{

        @Bean
        @Procedure(name="insertar_cliente")
        void insertar_cliente(
        @Param("ruc_param") String ruc,
        @Param("razon_social_param")String razon_social,
        @Param("email_param")String email,
        @Param("telefono_param")String telefono,
        @Param("direccion_param") String direccion
        );

        @Procedure(name="BuscarClientesPorRazonSocial")
        List<Cliente> BuscarClientesPorRazonSocial(String razon_social);

        @Procedure(name = "RazonSocial")
        List<String> RazonSocial();
        

        //List<Cliente> findByRazon_social(String razon_social);
}
