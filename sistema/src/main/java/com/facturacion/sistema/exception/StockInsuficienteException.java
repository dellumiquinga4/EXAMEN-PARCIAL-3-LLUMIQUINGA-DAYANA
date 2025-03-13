package com.facturacion.sistema.exception;

import java.math.BigDecimal;

public class StockInsuficienteException extends RuntimeException {
    
    private final String codProducto;
    private final String nombreProducto;
    private final BigDecimal stockDisponible;
    private final BigDecimal cantidadSolicitada;
    
    public StockInsuficienteException(String codProducto, String nombreProducto, 
                                      BigDecimal stockDisponible, BigDecimal cantidadSolicitada) {
        super();
        this.codProducto = codProducto;
        this.nombreProducto = nombreProducto;
        this.stockDisponible = stockDisponible;
        this.cantidadSolicitada = cantidadSolicitada;
    }
    
    @Override
    public String getMessage() {
        return "Stock insuficiente para el producto " + nombreProducto + " (" + codProducto + "). " +
                "Stock disponible: " + stockDisponible + ", Cantidad solicitada: " + cantidadSolicitada;
    }
} 