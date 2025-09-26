package com.example.lab05_20222297.repository;

import com.example.lab05_20222297.entity.usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<usuarios, Integer> {
    
    // Buscar por correo (para validaciones futuras)
    usuarios findByCorreo(String correo);
    
    // Verificar si existe un correo
    boolean existsByCorreo(String correo);
}