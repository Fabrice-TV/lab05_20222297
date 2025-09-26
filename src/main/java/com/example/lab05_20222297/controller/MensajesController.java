package com.example.lab05_20222297.controller;

import com.example.lab05_20222297.entity.usuarios;
import com.example.lab05_20222297.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MensajesController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    // Página de envío de mensajes
    @GetMapping("/envio-mensaje")
    public String envioMensaje(@RequestParam(name = "destinatario", required = false) Integer destinatarioId,
                               Model model) {
        try {
            // Obtener todos los usuarios para el select de destinatarios
            List<usuarios> listaUsuarios = usuariosRepository.findAll();
            model.addAttribute("usuarios", listaUsuarios);
            
            // Si viene un destinatario preseleccionado
            if (destinatarioId != null) {
                Optional<usuarios> destinatario = usuariosRepository.findById(destinatarioId);
                if (destinatario.isPresent()) {
                    model.addAttribute("destinatarioSeleccionado", destinatario.get());
                }
            }
            
            return "envio_mensaje";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar página de mensajes: " + e.getMessage());
            return "envio_mensaje";
        }
    }

    // Ruta temporal para ranking
    @GetMapping("/ranking")
    public String ranking(Model model) {
        model.addAttribute("mensaje", "Página de ranking en construcción...");
        return "test"; // Reutilizamos la página de test temporalmente
    }
}