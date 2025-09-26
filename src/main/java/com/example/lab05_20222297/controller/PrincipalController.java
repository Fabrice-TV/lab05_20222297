package com.example.lab05_20222297.controller;

import com.example.lab05_20222297.entity.usuarios;
import com.example.lab05_20222297.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PrincipalController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    // Ruta raíz - redirige a principal
    @GetMapping("/")
    public String index() {
        return "redirect:/principal";
    }

    // Página principal con lista de usuarios
    @GetMapping("/principal")
    public String principal(Model model) {
        try {
            List<usuarios> listaUsuarios = usuariosRepository.findAll();
            model.addAttribute("usuarios", listaUsuarios);
            
            // Agregar información adicional para debugging
            model.addAttribute("totalUsuarios", listaUsuarios.size());
            
            return "principal";
        } catch (Exception e) {
            // En caso de error, enviar lista vacía y mensaje de error
            model.addAttribute("usuarios", List.of());
            model.addAttribute("error", "Error al cargar usuarios: " + e.getMessage());
            return "principal";
        }
    }

    // Ruta de prueba para verificar conectividad
    @GetMapping("/test")
    public String test(Model model) {
        try {
            long totalUsuarios = usuariosRepository.count();
            model.addAttribute("mensaje", "¡Conexión exitosa! Total de usuarios: " + totalUsuarios);
            return "test";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error de conexión: " + e.getMessage());
            return "test";
        }
    }
}