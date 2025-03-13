package com.facturacion.sistema.exception;

public class FacturaNotFoundException extends RuntimeException {
    
    private final Integer codFactura;
    
    public FacturaNotFoundException(Integer codFactura) {
        super();
        this.codFactura = codFactura;
    }
    
    @Override
    public String getMessage() {
        return "No se encontró ninguna factura con el código: " + codFactura;
    }
} 