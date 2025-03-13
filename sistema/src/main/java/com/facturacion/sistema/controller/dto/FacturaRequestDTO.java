package com.facturacion.sistema.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class FacturaRequestDTO {
    
    @NotBlank(message = "El tipo de identificación es requerido")
    @Pattern(regexp = "CED|RUC|PAS", message = "El tipo de identificación debe ser CED, RUC o PAS")
    private String tipoIdentificacion;
    
    @NotBlank(message = "La identificación es requerida")
    @Size(min = 5, max = 20, message = "La identificación debe tener entre 5 y 20 caracteres")
    private String identificacion;
    
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;
    
    @NotEmpty(message = "La factura debe tener al menos un detalle")
    private List<DetalleRequestDTO> detalles;
} 