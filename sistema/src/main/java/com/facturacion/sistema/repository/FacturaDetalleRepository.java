package com.facturacion.sistema.repository;

import com.facturacion.sistema.model.FacturaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaDetalleRepository extends JpaRepository<FacturaDetalle, Integer> {
    
    List<FacturaDetalle> findByFacturaCodFactura(Integer codFactura);
    
    List<FacturaDetalle> findByCodProducto(String codProducto);
} 