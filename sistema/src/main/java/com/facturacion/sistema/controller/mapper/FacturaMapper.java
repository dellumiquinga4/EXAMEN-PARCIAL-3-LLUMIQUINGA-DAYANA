package com.facturacion.sistema.controller.mapper;

import com.facturacion.sistema.controller.dto.FacturaDTO;
import com.facturacion.sistema.controller.dto.FacturaDetalleDTO;
import com.facturacion.sistema.controller.dto.FacturaRequestDTO;
import com.facturacion.sistema.model.Factura;
import com.facturacion.sistema.model.FacturaDetalle;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FacturaMapper {
    
    private final DetalleMapper detalleMapper;
    
    public FacturaMapper(DetalleMapper detalleMapper) {
        this.detalleMapper = detalleMapper;
    }
    
    public Factura toEntity(FacturaRequestDTO dto) {
        Factura factura = new Factura();
        factura.setTipoIdentificacion(dto.getTipoIdentificacion());
        factura.setIdentificacion(dto.getIdentificacion());
        factura.setNombre(dto.getNombre());
        factura.setFecha(LocalDateTime.now());
        return factura;
    }
    
    public FacturaDTO toDTO(Factura entity) {
        FacturaDTO dto = new FacturaDTO();
        dto.setCodFactura(entity.getCodFactura());
        dto.setTipoIdentificacion(entity.getTipoIdentificacion());
        dto.setIdentificacion(entity.getIdentificacion());
        dto.setNombre(entity.getNombre());
        dto.setFecha(entity.getFecha());
        dto.setSubtotal(entity.getSubtotal());
        dto.setIva(entity.getIva());
        dto.setTotal(entity.getTotal());
        
        if (entity.getDetalles() != null) {
            dto.setDetalles(entity.getDetalles().stream()
                    .map(detalleMapper::toDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setDetalles(new ArrayList<>());
        }
        
        return dto;
    }
} 