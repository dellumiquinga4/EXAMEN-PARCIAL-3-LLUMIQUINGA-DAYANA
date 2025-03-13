package com.facturacion.sistema.controller.mapper;

import com.facturacion.sistema.controller.dto.DetalleRequestDTO;
import com.facturacion.sistema.controller.dto.FacturaDetalleDTO;
import com.facturacion.sistema.controller.dto.ProductoDTO;
import com.facturacion.sistema.model.Factura;
import com.facturacion.sistema.model.FacturaDetalle;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DetalleMapper {
    
    private static final BigDecimal IVA_RATE = new BigDecimal("0.12");
    
    public FacturaDetalle toEntity(DetalleRequestDTO dto, Factura factura, ProductoDTO producto) {
        FacturaDetalle detalle = new FacturaDetalle();
        detalle.setFactura(factura);
        detalle.setCodProducto(dto.getCodProducto());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(producto.getPrecio());
        
        // Calcular subtotal
        BigDecimal subtotal = producto.getPrecio().multiply(dto.getCantidad())
                .setScale(2, RoundingMode.HALF_UP);
        detalle.setSubtotal(subtotal);
        
        // Calcular IVA
        BigDecimal iva = subtotal.multiply(IVA_RATE).setScale(2, RoundingMode.HALF_UP);
        detalle.setIva(iva);
        
        // Calcular total
        BigDecimal total = subtotal.add(iva).setScale(2, RoundingMode.HALF_UP);
        detalle.setTotal(total);
        
        return detalle;
    }
    
    public FacturaDetalleDTO toDTO(FacturaDetalle entity) {
        FacturaDetalleDTO dto = new FacturaDetalleDTO();
        dto.setCodDetalle(entity.getCodDetalle());
        dto.setCodFactura(entity.getFactura().getCodFactura());
        dto.setCodProducto(entity.getCodProducto());
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setSubtotal(entity.getSubtotal());
        dto.setIva(entity.getIva());
        dto.setTotal(entity.getTotal());
        return dto;
    }
} 