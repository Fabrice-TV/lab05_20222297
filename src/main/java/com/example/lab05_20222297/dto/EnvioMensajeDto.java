package com.example.lab05_20222297.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioMensajeDto {

    @NotNull(message = "Debe seleccionar un destinatario")
    private Integer destinatarioId;

    @NotBlank(message = "Debe seleccionar un tipo de regalo")
    @Pattern(regexp = "^(Flor|Carrito)$", message = "Solo se permite 'Flor' o 'Carrito'")
    private String regaloTipo;

    // Campo opcional - solo para carrito (para flor siempre será amarillo)
    private String regaloColor;

    @NotBlank(message = "El mensaje motivacional es obligatorio")
    @Size(min = 20, max = 255, message = "El mensaje debe tener entre 20 y 255 caracteres")
    private String contenido;

    // Método para validar que no contenga palabras prohibidas
    public boolean contienesPalabrasProhibidas() {
        if (contenido == null) return false;
        
        String mensajeMinuscula = contenido.toLowerCase();
        return mensajeMinuscula.contains("odio") || mensajeMinuscula.contains("feo");
    }

    // Método para obtener el color final del regalo
    public String getColorFinal() {
        if ("Flor".equals(regaloTipo)) {
            return "Amarillo"; // Flor siempre amarilla
        } else if ("Carrito".equals(regaloTipo)) {
            return regaloColor != null ? regaloColor : "Rojo"; // Color por defecto para carrito
        }
        return null;
    }

    // Validar que si es carrito, tenga color seleccionado
    public boolean isCarritoSinColor() {
        return "Carrito".equals(regaloTipo) && (regaloColor == null || regaloColor.trim().isEmpty());
    }
}