package com.example.lab05_20222297.controller;

import com.example.lab05_20222297.dto.RegistroUsuarioDto;
import com.example.lab05_20222297.entity.usuarios;
import com.example.lab05_20222297.repository.UsuariosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistroController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    // Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("registroDto", new RegistroUsuarioDto());
        return "registro";
    }

    // Procesar el formulario de registro
    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("registroDto") RegistroUsuarioDto registroDto,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        // Validar que las contraseñas coincidan
        if (!registroDto.isContrasenaValida()) {
            bindingResult.rejectValue("confirmarContrasena", "error.confirmarContrasena", 
                                    "Las contraseñas no coinciden");
        }

        // Validar que el correo no esté duplicado
        if (usuariosRepository.existsByCorreo(registroDto.getCorreo())) {
            bindingResult.rejectValue("correo", "error.correo", 
                                    "Ya existe un usuario registrado con este correo electrónico");
        }

        // Si hay errores de validación, regresar al formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("registroDto", registroDto);
            return "registro";
        }

        try {
            // Crear nuevo usuario
            usuarios nuevoUsuario = new usuarios();
            nuevoUsuario.setNombre(registroDto.getNombre());
            nuevoUsuario.setApellido(registroDto.getApellido());
            nuevoUsuario.setCorreo(registroDto.getCorreo());
            nuevoUsuario.setEdad(registroDto.getEdad());
            nuevoUsuario.setDescripcion(registroDto.getDescripcion());
            nuevoUsuario.setContrasena(registroDto.getContrasena()); // En producción, hashear la contraseña

            // Guardar en la base de datos
            usuariosRepository.save(nuevoUsuario);

            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("mensajeExito", 
                "¡Registro exitoso! Bienvenido/a " + registroDto.getNombre() + " " + registroDto.getApellido());

            return "redirect:/principal";

        } catch (Exception e) {
            // En caso de error al guardar
            model.addAttribute("error", "Error al procesar el registro: " + e.getMessage());
            model.addAttribute("registroDto", registroDto);
            return "registro";
        }
    }
}