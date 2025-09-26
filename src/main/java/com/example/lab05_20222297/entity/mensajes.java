package com.example.lab05_20222297.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;

public class mensajes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(nullable = false)
    private int remitente_id;
    private int destinatario_id;

    @Column(nullable = false)
    private String regalo_tipo;

    @Column(nullable = false)
    @Size(max = 30,message = "Solo se soportan 30 caractéres")
    private String regalo_color;

    @Column(nullable = false)
    @Size(max = 255,message = "Solo se soportan 255 caractéres")
    private String contenido;

    @Column
    private Timestamp fecha_envio;


}
