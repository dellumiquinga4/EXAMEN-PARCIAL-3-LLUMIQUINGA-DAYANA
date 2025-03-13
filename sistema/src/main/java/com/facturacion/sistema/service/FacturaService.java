package com.facturacion.sistema.service;

import com.facturacion.sistema.client.ProductoClient;
import com.facturacion.sistema.controller.dto.DetalleRequestDTO;
import com.facturacion.sistema.controller.dto.FacturaRequestDTO;
import com.facturacion.sistema.controller.dto.ProductoDTO;
import com.facturacion.sistema.controller.mapper.DetalleMapper;
import com.facturacion.sistema.controller.mapper.FacturaMapper;
import com.facturacion.sistema.exception.FacturaNotFoundException;
import com.facturacion.sistema.exception.ProductoNotFoundException;
import com.facturacion.sistema.exception.StockInsuficienteException;
import com.facturacion.sistema.model.Factura;
import com.facturacion.sistema.model.FacturaDetalle;
import com.facturacion.sistema.repository.FacturaDetalleRepository;
import com.facturacion.sistema.repository.FacturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final FacturaDetalleRepository facturaDetalleRepository;
    private final ProductoClient productoClient;
    private final FacturaMapper facturaMapper;
    private final DetalleMapper detalleMapper;

    public FacturaService(FacturaRepository facturaRepository,
                          FacturaDetalleRepository facturaDetalleRepository,
                          ProductoClient productoClient,
                          FacturaMapper facturaMapper,
                          DetalleMapper detalleMapper) {
        this.facturaRepository = facturaRepository;
        this.facturaDetalleRepository = facturaDetalleRepository;
        this.productoClient = productoClient;
        this.facturaMapper = facturaMapper;
        this.detalleMapper = detalleMapper;
    }

    public List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    public Factura obtenerFacturaPorId(Integer id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new FacturaNotFoundException(id));
    }

    public List<Factura> obtenerFacturasPorIdentificacion(String identificacion) {
        return facturaRepository.findByIdentificacion(identificacion);
    }

    @Transactional
    public Factura crearFactura(FacturaRequestDTO facturaRequestDTO) {
        log.info("Iniciando creación de factura para el cliente: {}", facturaRequestDTO.getNombre());
        
        // Crear la factura a partir del DTO
        Factura factura = facturaMapper.toEntity(facturaRequestDTO);
        
        // Inicializar las listas y los valores acumulados
        List<FacturaDetalle> detalles = new ArrayList<>();
        BigDecimal subtotalFactura = BigDecimal.ZERO;
        BigDecimal ivaFactura = BigDecimal.ZERO;
        BigDecimal totalFactura = BigDecimal.ZERO;
        
        // Procesar cada detalle
        for (DetalleRequestDTO detalleDTO : facturaRequestDTO.getDetalles()) {
            try {
                // Obtener información del producto
                ProductoDTO producto = productoClient.buscarProductoPorCodigo(detalleDTO.getCodProducto());
                
                if (producto == null) {
                    throw new ProductoNotFoundException(detalleDTO.getCodProducto());
                }
                
                // Verificar stock
                if (producto.getExistencia().compareTo(detalleDTO.getCantidad()) < 0) {
                    throw new StockInsuficienteException(
                            producto.getCodProducto(),
                            producto.getNombre(),
                            producto.getExistencia(),
                            detalleDTO.getCantidad()
                    );
                }
                
                // Crear el detalle de factura
                FacturaDetalle detalle = detalleMapper.toEntity(detalleDTO, factura, producto);
                detalles.add(detalle);
                
                // Acumular totales
                subtotalFactura = subtotalFactura.add(detalle.getSubtotal());
                ivaFactura = ivaFactura.add(detalle.getIva());
                totalFactura = totalFactura.add(detalle.getTotal());
                
            } catch (Exception e) {
                log.error("Error al procesar detalle de factura: {}", e.getMessage());
                throw e;
            }
        }
        
        // Asignar totales a la factura
        factura.setSubtotal(subtotalFactura.setScale(2, RoundingMode.HALF_UP));
        factura.setIva(ivaFactura.setScale(2, RoundingMode.HALF_UP));
        factura.setTotal(totalFactura.setScale(2, RoundingMode.HALF_UP));
        factura.setDetalles(detalles);
        
        // Guardar la factura
        log.info("Guardando factura con {} detalles, total: {}", detalles.size(), totalFactura);
        return facturaRepository.save(factura);
    }
} 