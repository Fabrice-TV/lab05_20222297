package com.example.lab05_20222297.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "ranking")
@Getter
@Setter
public class ranking {
    @Id
    @Column(name = "usuario_id")
    private int usuarioId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    private usuarios usuario;

    @Column(name = "total_regalos")
    private int totalRegalos = 0;
}
