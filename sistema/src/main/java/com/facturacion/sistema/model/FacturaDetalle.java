package com.facturacion.sistema.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "factura_detalle")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "codDetalle")
public class FacturaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_detalle")
    private Integer codDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_factura", nullable = false)
    private Factura factura;

    @Column(name = "cod_producto", length = 32, nullable = false)
    private String codProducto;

    @Column(name = "cantidad", precision = 5, scale = 0, nullable = false)
    private BigDecimal cantidad;

    @Column(name = "precio_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "iva", precision = 10, scale = 2, nullable = false)
    private BigDecimal iva;

    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    private BigDecimal total;
} 