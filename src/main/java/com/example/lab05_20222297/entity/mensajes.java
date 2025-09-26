package com.example.lab05_20222297.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "mensajes")
@Getter
@Setter
public class mensajes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "remitente_id", nullable = false)
    private usuarios remitente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private usuarios destinatario;

    @Enumerated(EnumType.STRING)
    @Column(name = "regalo_tipo", nullable = false)
    private RegaloTipo regaloTipo;

    @Column(name = "regalo_color")
    @Size(max = 30, message = "Solo se soportan 30 caracteres")
    private String regaloColor;

    @Column(nullable = false)
    @Size(max = 255, message = "Solo se soportan 255 caracteres")
    @NotBlank
    private String contenido;

    @Column(name = "fecha_envio")
    @CreationTimestamp
    private Timestamp fechaEnvio;

    public enum RegaloTipo {
        Flor, Carrito
    }
}
