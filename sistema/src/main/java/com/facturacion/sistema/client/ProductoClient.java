package com.facturacion.sistema.client;

import com.facturacion.sistema.controller.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producto-service", url = "http://localhost:8081")
public interface ProductoClient {
    
    @GetMapping("/v1/productos/{codProducto}")
    ProductoDTO buscarProductoPorCodigo(@PathVariable("codProducto") String codProducto);
} 