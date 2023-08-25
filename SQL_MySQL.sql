-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-08-2023 a las 11:55:07
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bitacore`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarClientesPorRazonSocial` (IN `razonSocialParam` VARCHAR(255))   BEGIN
    SELECT * FROM clientes WHERE razon_social like razonSocialParam;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `BuscarProductosPorNombre` (IN `nombreParam` VARCHAR(255))   BEGIN
    SELECT * FROM productos WHERE nombre = nombreParam;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarVendedoresPorNombre` (IN `nombreCompletoParam` VARCHAR(255))   BEGIN
    SELECT * FROM vendedores WHERE nombres = nombreCompletoParam;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `crear_pedido` (IN `cliente_id_param` INT, IN `vendedor_id_param` INT, IN `forma_pago_param` VARCHAR(50), IN `dias_pago_param` INT, IN `direccion_entrega_param` VARCHAR(255), IN `detalles` JSON)   BEGIN
    DECLARE pedido_id INT;
    DECLARE producto_id INT;
    DECLARE cantidad INT;
    DECLARE precio_unitario DECIMAL(10, 2);
    
    -- Insertar en la tabla pedidos
    INSERT INTO pedidos (fecha_emision,cliente_id, vendedor_id, forma_pago, dias_pago, direccion_entrega)
    VALUES (now(),cliente_id_param, vendedor_id_param, forma_pago_param, dias_pago_param, direccion_entrega_param);
    
    SET pedido_id = LAST_INSERT_ID();
    
    -- Iterar sobre los detalles del pedido y agregarlos a la tabla detalle_pedido
    WHILE JSON_LENGTH(detalles) > 0 DO
        SET producto_id = JSON_UNQUOTE(JSON_EXTRACT(detalles, '$[0].productoId'));
        SET cantidad = JSON_UNQUOTE(JSON_EXTRACT(detalles, '$[0].cantidad'));
        
        -- Obtener precio unitario del producto
        SET precio_unitario = (SELECT precio FROM productos WHERE id = producto_id);
        
        -- Insertar en la tabla detalle_pedido
        INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, precio_unitario)
        VALUES (pedido_id, producto_id, cantidad, precio_unitario);
        
        -- Actualizar stock del producto
        UPDATE productos SET stock = stock - cantidad WHERE id = producto_id;
        
        -- Eliminar el primer elemento del arreglo
        SET detalles = JSON_REMOVE(detalles, '$[0]');
    END WHILE;
    
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertar_cliente` (IN `ruc_param` VARCHAR(11), IN `razon_social_param` VARCHAR(255), IN `email_param` VARCHAR(255), IN `telefono_param` VARCHAR(20), IN `direccion_param` VARCHAR(255))   BEGIN
    INSERT INTO clientes (`ruc`, `razon_social`, `email`, `telefono`, `direccion`)
    VALUES (ruc_param, razon_social_param, email_param, telefono_param, direccion_param);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertar_producto` (IN `nombre_param` VARCHAR(255), IN `descripcion_param` VARCHAR(255), IN `precio_param` DECIMAL(10,2), IN `stock_param` INT)   BEGIN
    INSERT INTO productos (`nombre`, `descripcion`, `precio`, `stock`)
    VALUES (nombre_param, descripcion_param, precio_param, stock_param);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `RazonSocial` ()   BEGIN
    SELECT razon_social FROM clientes;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `ruc` varchar(11) DEFAULT NULL,
  `razon_social` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estructura de tabla para la tabla `detalle_pedido`
--

CREATE TABLE `detalle_pedido` (
  `id` int(11) NOT NULL,
  `pedido_id` int(11) DEFAULT NULL,
  `producto_id` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `precio_unitario` decimal(10,2) DEFAULT NULL,
  `subTotal` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id` int(11) NOT NULL,
  `codigo_pedido` varchar(20) NOT NULL,
  `fecha_emision` date NOT NULL,
  `cliente_id` int(11) DEFAULT NULL,
  `razon_social_cliente` varchar(255) DEFAULT NULL,
  `vendedor_id` int(11) DEFAULT NULL,
  `forma_pago` varchar(50) DEFAULT NULL,
  `dias_pago` int(11) DEFAULT NULL,
  `direccion_entrega` varchar(255) DEFAULT NULL,
  `detalles` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `precio` decimal(10,2) NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estructura de tabla para la tabla `vendedores`
--

CREATE TABLE `vendedores` (
  `id` int(11) NOT NULL,
  `nombres` varchar(255) NOT NULL,
  `apellidos` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_ruc_unique` (`ruc`);

--
-- Indices de la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pedido_id` (`pedido_id`),
  ADD KEY `producto_id` (`producto_id`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cliente_id` (`cliente_id`),
  ADD KEY `vendedor_id` (`vendedor_id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `vendedores`
--
ALTER TABLE `vendedores`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `vendedores`
--
ALTER TABLE `vendedores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  ADD CONSTRAINT `detalle_pedido_ibfk_1` FOREIGN KEY (`pedido_id`) REFERENCES `pedidos` (`id`),
  ADD CONSTRAINT `detalle_pedido_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`);

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`),
  ADD CONSTRAINT `pedidos_ibfk_2` FOREIGN KEY (`vendedor_id`) REFERENCES `vendedores` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


--Store Procedure

CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarClientesPorRazonSocial`(IN razonSocialParam VARCHAR(255))
BEGIN
    SELECT * FROM clientes WHERE razon_social like razonSocialParam;
END


CREATE DEFINER=`root`@`localhost` PROCEDURE `BuscarProductosPorNombre`(IN nombreParam VARCHAR(255))
BEGIN
    SELECT * FROM productos WHERE nombre = nombreParam;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarVendedoresPorNombre`(IN nombreCompletoParam VARCHAR(255))
BEGIN
    SELECT * FROM vendedores WHERE nombres = nombreCompletoParam;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `crear_pedido`(IN `cliente_id_param` INT, IN `vendedor_id_param` INT, IN `forma_pago_param` VARCHAR(50), IN `dias_pago_param` INT, IN `direccion_entrega_param` VARCHAR(255), IN `detalles` JSON)
BEGIN
    DECLARE pedido_id INT;
    DECLARE producto_id INT;
    DECLARE cantidad INT;
    DECLARE precio_unitario DECIMAL(10, 2);
    
    INSERT INTO pedidos (fecha_emision,cliente_id, vendedor_id, forma_pago, dias_pago, direccion_entrega)
    VALUES (now(),cliente_id_param, vendedor_id_param, forma_pago_param, dias_pago_param, direccion_entrega_param);
    
    SET pedido_id = LAST_INSERT_ID();
    
    WHILE JSON_LENGTH(detalles) > 0 DO
        SET producto_id = JSON_UNQUOTE(JSON_EXTRACT(detalles, '$[0].productoId'));
        SET cantidad = JSON_UNQUOTE(JSON_EXTRACT(detalles, '$[0].cantidad'));
        
        SET precio_unitario = (SELECT precio FROM productos WHERE id = producto_id);
        
        INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, precio_unitario)
        VALUES (pedido_id, producto_id, cantidad, precio_unitario);
        
        UPDATE productos SET stock = stock - cantidad WHERE id = producto_id;
        
        SET detalles = JSON_REMOVE(detalles, '$[0]');
    END WHILE;
    
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertar_cliente`(
    IN ruc_param VARCHAR(11),
    IN razon_social_param VARCHAR(255),
    IN email_param VARCHAR(255),
    IN telefono_param VARCHAR(20),
    IN direccion_param VARCHAR(255)
)
BEGIN
    INSERT INTO clientes (`ruc`, `razon_social`, `email`, `telefono`, `direccion`)
    VALUES (ruc_param, razon_social_param, email_param, telefono_param, direccion_param);
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertar_producto`(
    IN nombre_param VARCHAR(255),
    IN descripcion_param VARCHAR(255),
    IN precio_param DECIMAL(10, 2),
    IN stock_param INT
)
BEGIN
    INSERT INTO productos (`nombre`, `descripcion`, `precio`, `stock`)
    VALUES (nombre_param, descripcion_param, precio_param, stock_param);
END


CREATE DEFINER=`root`@`localhost` PROCEDURE `RazonSocial`()
BEGIN
    SELECT razon_social FROM clientes;
END