package com.facturacion.sistema.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "factura")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "codFactura")
@ToString(exclude = "detalles")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_factura")
    private Integer codFactura;

    @Column(name = "tipo_identificacion", length = 3, nullable = false)
    private String tipoIdentificacion;

    @Column(name = "identificacion", length = 20, nullable = false)
    private String identificacion;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "subtotal", precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "iva", precision = 10, scale = 2, nullable = false)
    private BigDecimal iva;

    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FacturaDetalle> detalles;
} 