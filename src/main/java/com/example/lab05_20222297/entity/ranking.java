package com.example.lab05_20222297.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "ranking")
@Getter
@Setter

public class ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private int usuario_id;


    @NotBlank
    private int  total_regalos;
}
