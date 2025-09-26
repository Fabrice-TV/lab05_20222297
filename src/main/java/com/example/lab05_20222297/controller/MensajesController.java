package com.example.lab05_20222297.controller;

import com.example.lab05_20222297.dto.EnvioMensajeDto;
import com.example.lab05_20222297.entity.mensajes;
import com.example.lab05_20222297.entity.ranking;
import com.example.lab05_20222297.entity.usuarios;
import com.example.lab05_20222297.repository.MensajesRepository;
import com.example.lab05_20222297.repository.RankingRepository;
import com.example.lab05_20222297.repository.UsuariosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class MensajesController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private MensajesRepository mensajesRepository;

    @Autowired
    private RankingRepository rankingRepository;

    // Mostrar formulario de envío de mensajes
    @GetMapping("/envio-mensaje")
    public String mostrarFormularioMensaje(@RequestParam(name = "destinatario", required = false) Integer destinatarioId,
                                          Model model) {
        try {
            // Crear DTO del mensaje
            EnvioMensajeDto mensajeDto = new EnvioMensajeDto();
            
            // Obtener todos los usuarios para el select de destinatarios
            List<usuarios> listaUsuarios = usuariosRepository.findAll();
            model.addAttribute("usuarios", listaUsuarios);
            
            // Si viene un destinatario preseleccionado
            if (destinatarioId != null) {
                Optional<usuarios> destinatario = usuariosRepository.findById(destinatarioId);
                if (destinatario.isPresent()) {
                    model.addAttribute("destinatarioSeleccionado", destinatario.get());
                    mensajeDto.setDestinatarioId(destinatarioId); // Pre-seleccionar en el DTO
                }
            }
            
            model.addAttribute("mensajeDto", mensajeDto);
            return "envio_mensaje";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar página de mensajes: " + e.getMessage());
            model.addAttribute("mensajeDto", new EnvioMensajeDto());
            model.addAttribute("usuarios", List.of());
            return "envio_mensaje";
        }
    }

    // Procesar el envío del mensaje
    @PostMapping("/envio-mensaje")
    public String procesarEnvioMensaje(@Valid @ModelAttribute("mensajeDto") EnvioMensajeDto mensajeDto,
                                      BindingResult bindingResult,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        
        try {
            // Validar palabras prohibidas
            if (mensajeDto.contienesPalabrasProhibidas()) {
                bindingResult.rejectValue("contenido", "error.contenido", 
                                        "El mensaje no debe contener las palabras 'odio' o 'feo'");
            }

            // Validar que si es carrito tenga color
            if (mensajeDto.isCarritoSinColor()) {
                bindingResult.rejectValue("regaloColor", "error.regaloColor", 
                                        "Debe seleccionar un color para el carrito");
            }

            // Validar que el destinatario exista
            Optional<usuarios> destinatario = usuariosRepository.findById(mensajeDto.getDestinatarioId());
            if (!destinatario.isPresent()) {
                bindingResult.rejectValue("destinatarioId", "error.destinatarioId", 
                                        "El destinatario seleccionado no existe");
            }

            // Si hay errores, regresar al formulario
            if (bindingResult.hasErrors()) {
                List<usuarios> listaUsuarios = usuariosRepository.findAll();
                model.addAttribute("usuarios", listaUsuarios);
                
                // Mantener destinatario seleccionado
                if (mensajeDto.getDestinatarioId() != null) {
                    Optional<usuarios> dest = usuariosRepository.findById(mensajeDto.getDestinatarioId());
                    dest.ifPresent(usuario -> model.addAttribute("destinatarioSeleccionado", usuario));
                }
                
                return "envio_mensaje";
            }

            // Crear el mensaje
            mensajes nuevoMensaje = new mensajes();
            
            // Por simplicidad, asumiremos que el remitente es el primer usuario
            // En una app real, obtendríamos del usuario logueado
            usuarios remitente = usuariosRepository.findAll().get(0);
            
            nuevoMensaje.setRemitente(remitente);
            nuevoMensaje.setDestinatario(destinatario.get());
            nuevoMensaje.setContenido(mensajeDto.getContenido());
            
            // Configurar el regalo
            if ("Flor".equals(mensajeDto.getRegaloTipo())) {
                nuevoMensaje.setRegaloTipo(mensajes.RegaloTipo.Flor);
                nuevoMensaje.setRegaloColor("Amarillo"); // Flor siempre amarilla
            } else {
                nuevoMensaje.setRegaloTipo(mensajes.RegaloTipo.Carrito);
                nuevoMensaje.setRegaloColor(mensajeDto.getRegaloColor());
            }

            // Guardar mensaje
            mensajesRepository.save(nuevoMensaje);

            // Actualizar ranking del destinatario
            actualizarRankingDestinatario(destinatario.get());

            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("mensajeExito", 
                "¡Mensaje enviado exitosamente a " + destinatario.get().getNombre() + " " + 
                destinatario.get().getApellido() + "! 🎉");
            
            return "redirect:/principal";

        } catch (Exception e) {
            // Error al procesar
            model.addAttribute("error", "Error al enviar el mensaje: " + e.getMessage());
            List<usuarios> listaUsuarios = usuariosRepository.findAll();
            model.addAttribute("usuarios", listaUsuarios);
            
            // Mantener destinatario seleccionado
            if (mensajeDto.getDestinatarioId() != null) {
                Optional<usuarios> dest = usuariosRepository.findById(mensajeDto.getDestinatarioId());
                dest.ifPresent(usuario -> model.addAttribute("destinatarioSeleccionado", usuario));
            }
            
            return "envio_mensaje";
        }
    }

    // Método helper para actualizar el ranking del destinatario
    private void actualizarRankingDestinatario(usuarios destinatario) {
        // Buscar ranking existente del destinatario
        Optional<ranking> rankingExistente = rankingRepository.findByUsuario(destinatario);
        
        if (rankingExistente.isPresent()) {
            // Si existe, incrementar el total de regalos
            ranking rankingUsuario = rankingExistente.get();
            rankingUsuario.setTotalRegalos(rankingUsuario.getTotalRegalos() + 1);
            rankingRepository.save(rankingUsuario);
        } else {
            // Si no existe, crear nuevo registro de ranking
            ranking nuevoRanking = new ranking();
            nuevoRanking.setUsuario(destinatario);
            nuevoRanking.setTotalRegalos(1);
            rankingRepository.save(nuevoRanking);
        }
    }
}