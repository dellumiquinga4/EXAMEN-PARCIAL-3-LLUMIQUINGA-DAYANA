package com.facturacion.sistema.controller;

import com.facturacion.sistema.controller.dto.FacturaDTO;
import com.facturacion.sistema.controller.dto.FacturaRequestDTO;
import com.facturacion.sistema.controller.mapper.FacturaMapper;
import com.facturacion.sistema.exception.FacturaNotFoundException;
import com.facturacion.sistema.exception.ProductoNotFoundException;
import com.facturacion.sistema.exception.StockInsuficienteException;
import com.facturacion.sistema.model.Factura;
import com.facturacion.sistema.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/facturas")
@Tag(name = "Facturas", description = "API para la gestión de facturas")
@Slf4j
public class FacturaController {

    private final FacturaService facturaService;
    private final FacturaMapper facturaMapper;

    public FacturaController(FacturaService facturaService, FacturaMapper facturaMapper) {
        this.facturaService = facturaService;
        this.facturaMapper = facturaMapper;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las facturas", description = "Retorna una lista con todas las facturas")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", 
            content = @Content(schema = @Schema(implementation = FacturaDTO.class)))
    public ResponseEntity<List<FacturaDTO>> obtenerTodasLasFacturas() {
        List<Factura> facturas = facturaService.obtenerTodasLasFacturas();
        List<FacturaDTO> dtos = facturas.stream()
                .map(facturaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener factura por ID", description = "Retorna una factura según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Factura encontrada", 
                content = @Content(schema = @Schema(implementation = FacturaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada", content = @Content)
    })
    public ResponseEntity<FacturaDTO> obtenerFacturaPorId(
            @Parameter(description = "ID de la factura", required = true) @PathVariable("id") Integer id) {
        try {
            Factura factura = facturaService.obtenerFacturaPorId(id);
            return ResponseEntity.ok(facturaMapper.toDTO(factura));
        } catch (FacturaNotFoundException e) {
            log.error("Error al buscar factura: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/identificacion/{identificacion}")
    @Operation(summary = "Obtener facturas por identificación", 
               description = "Retorna una lista de facturas según la identificación del cliente")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", 
            content = @Content(schema = @Schema(implementation = FacturaDTO.class)))
    public ResponseEntity<List<FacturaDTO>> obtenerFacturasPorIdentificacion(
            @Parameter(description = "Identificación del cliente", required = true) 
            @PathVariable("identificacion") String identificacion) {
        List<Factura> facturas = facturaService.obtenerFacturasPorIdentificacion(identificacion);
        List<FacturaDTO> dtos = facturas.stream()
                .map(facturaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    @Operation(summary = "Crear factura", description = "Crea una nueva factura")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Factura creada correctamente", 
                content = @Content(schema = @Schema(implementation = FacturaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content),
        @ApiResponse(responseCode = "422", description = "Stock insuficiente", content = @Content)
    })
    public ResponseEntity<?> crearFactura(@Valid @RequestBody FacturaRequestDTO facturaRequestDTO) {
        try {
            Factura factura = facturaService.crearFactura(facturaRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(facturaMapper.toDTO(factura));
        } catch (ProductoNotFoundException e) {
            log.error("Error al crear factura: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (StockInsuficienteException e) {
            log.error("Error al crear factura: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error al crear factura: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}