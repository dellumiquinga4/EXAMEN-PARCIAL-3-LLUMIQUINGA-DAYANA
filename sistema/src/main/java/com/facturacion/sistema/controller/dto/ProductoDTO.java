package com.facturacion.sistema.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductoDTO {
    
    private String codProducto;
    
    private String nombre;
    
    private BigDecimal existencia;
    
    private BigDecimal precio;
} 