package com.facturacion.sistema.exception;

public class ProductoNotFoundException extends RuntimeException {
    
    private final String codProducto;
    
    public ProductoNotFoundException(String codProducto) {
        super();
        this.codProducto = codProducto;
    }
    
    @Override
    public String getMessage() {
        return "No se encontró ningún producto con el código: " + codProducto;
    }
} 