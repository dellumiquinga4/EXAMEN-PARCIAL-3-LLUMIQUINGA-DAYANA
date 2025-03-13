package com.facturacion.sistema.repository;

import com.facturacion.sistema.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    
    List<Factura> findByIdentificacion(String identificacion);
    
    List<Factura> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    List<Factura> findByTipoIdentificacionAndIdentificacion(String tipoIdentificacion, String identificacion);
} 