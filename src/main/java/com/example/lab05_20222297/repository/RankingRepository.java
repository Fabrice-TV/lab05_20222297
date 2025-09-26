package com.example.lab05_20222297.repository;

import com.example.lab05_20222297.entity.ranking;
import com.example.lab05_20222297.entity.usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<ranking, Integer> {
    
    // Buscar ranking por usuario
    Optional<ranking> findByUsuario(usuarios usuario);
    
    // Ranking ordenado por total de regalos (descendente)
    List<ranking> findAllByOrderByTotalRegalosDesc();
}