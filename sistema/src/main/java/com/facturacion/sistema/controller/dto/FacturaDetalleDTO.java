package com.facturacion.sistema.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class FacturaDetalleDTO {
    
    private Integer codDetalle;
    
    private Integer codFactura;
    
    @NotBlank(message = "El código de producto es requerido")
    private String codProducto;
    
    @NotNull(message = "La cantidad es requerida")
    @DecimalMin(value = "0.1", message = "La cantidad debe ser mayor a 0")
    private BigDecimal cantidad;
    
    private BigDecimal precioUnitario;
    
    private BigDecimal subtotal;
    
    private BigDecimal iva;
    
    private BigDecimal total;
} 