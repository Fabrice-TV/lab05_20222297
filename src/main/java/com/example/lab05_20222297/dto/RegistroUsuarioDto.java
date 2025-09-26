package com.example.lab05_20222297.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroUsuarioDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", 
             message = "El nombre solo puede contener letras y espacios, sin números ni caracteres especiales")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", 
             message = "El apellido solo puede contener letras y espacios, sin números ni caracteres especiales")
    private String apellido;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", 
             message = "Debe ingresar un correo electrónico válido (ejemplo: usuario@dominio.com)")
    private String correo;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "Debe ser mayor de edad (mínimo 18 años)")
    @Max(value = 120, message = "Edad no válida (máximo 120 años)")
    private Integer edad;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 255, message = "La descripción debe tener entre 10 y 255 caracteres")
    private String descripcion;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 255, message = "La contraseña debe tener al menos 6 caracteres")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$", 
             message = "La contraseña debe tener al menos 6 caracteres y contener al menos 1 número")
    private String contrasena;

    @NotBlank(message = "Debe confirmar la contraseña")
    private String confirmarContrasena;

    // Método para validar que las contraseñas coincidan
    public boolean isContrasenaValida() {
        return contrasena != null && contrasena.equals(confirmarContrasena);
    }
}