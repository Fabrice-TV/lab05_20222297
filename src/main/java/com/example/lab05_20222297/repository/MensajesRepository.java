package com.example.lab05_20222297.repository;

import com.example.lab05_20222297.entity.mensajes;
import com.example.lab05_20222297.entity.usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajesRepository extends JpaRepository<mensajes, Integer> {
    
    // Mensajes enviados por un usuario
    List<mensajes> findByRemitente(usuarios remitente);
    
    // Mensajes recibidos por un usuario
    List<mensajes> findByDestinatario(usuarios destinatario);
    
    // Contar mensajes recibidos por usuario (para ranking)
    @Query("SELECT COUNT(m) FROM mensajes m WHERE m.destinatario.id = ?1")
    Long contarMensajesRecibidos(Integer usuarioId);
}