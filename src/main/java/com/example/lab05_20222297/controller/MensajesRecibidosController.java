package com.example.lab05_20222297.controller;

import com.example.lab05_20222297.entity.mensajes;
import com.example.lab05_20222297.entity.usuarios;
import com.example.lab05_20222297.repository.MensajesRepository;
import com.example.lab05_20222297.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mensajes-recibidos")
public class MensajesRecibidosController {

    @Autowired
    private MensajesRepository mensajesRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @GetMapping
    public String mostrarMensajesRecibidos(@RequestParam(name = "usuario", required = false) Integer usuarioId,
                                         Model model,
                                         RedirectAttributes redirectAttributes) {
        try {
            // Si no se especifica usuario, tomar el primero (simulando usuario logueado)
            usuarios usuarioActual;
            if (usuarioId != null) {
                Optional<usuarios> usuario = usuariosRepository.findById(usuarioId);
                if (!usuario.isPresent()) {
                    redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
                    return "redirect:/principal";
                }
                usuarioActual = usuario.get();
            } else {
                // Por simplicidad, usar el primer usuario como "usuario logueado"
                List<usuarios> todosUsuarios = usuariosRepository.findAll();
                if (todosUsuarios.isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "No hay usuarios registrados");
                    return "redirect:/principal";
                }
                usuarioActual = todosUsuarios.get(0);
            }

            // Obtener mensajes recibidos ordenados por más recientes primero
            List<mensajes> mensajesRecibidos = mensajesRepository.findByDestinatarioOrderByIdDesc(usuarioActual);
            
            // Contar total de mensajes recibidos
            Long totalMensajes = mensajesRepository.countByDestinatario(usuarioActual);
            
            // Obtener todos los usuarios para el selector
            List<usuarios> todosUsuarios = usuariosRepository.findAll();

            // Agregar atributos al modelo
            model.addAttribute("usuarioActual", usuarioActual);
            model.addAttribute("mensajesRecibidos", mensajesRecibidos);
            model.addAttribute("totalMensajes", totalMensajes);
            model.addAttribute("todosUsuarios", todosUsuarios);

            return "mensajes_recibidos";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cargar mensajes: " + e.getMessage());
            return "redirect:/principal";
        }
    }
}