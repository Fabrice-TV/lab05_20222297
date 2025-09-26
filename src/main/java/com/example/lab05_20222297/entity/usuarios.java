package com.example.lab05_20222297.entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "usuarios")
@Getter
@Setter


public class usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(nullable = false)
    @Size(max = 50,message = "Solo se soportan 50 caractéres")
    @NotBlank
    private String nombre;

    @Column(nullable = false)
    @Size(max = 50,message = "Solo se soportan 50 caractéres")
    @NotBlank
    private String apellido;

    @Column(nullable = false, unique = true)
    @Size(max = 100,message = "Solo se soportan 100 caractéres")
    @NotBlank
    private String correo;

    @Column(nullable = false)
    private int edad;

    @Column(nullable = true)
    @Size(max = 255,message = "Solo se soportan 255 caractéres")
    private String descripcion;

    @Column(nullable = true)
    @Size(max = 255,message = "Solo se soportan 255 caractéres")
    private String contrasena;

}
