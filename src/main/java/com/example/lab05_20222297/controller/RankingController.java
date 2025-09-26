package com.example.lab05_20222297.controller;

import com.example.lab05_20222297.entity.ranking;
import com.example.lab05_20222297.repository.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    private RankingRepository rankingRepository;

    @GetMapping
    public String mostrarRanking(Model model) {
        // Obtener usuarios ordenados por total de regalos (descendente)
        List<ranking> rankingUsuarios = rankingRepository.findAllByOrderByTotalRegalosDesc();
        
        model.addAttribute("rankingUsuarios", rankingUsuarios);
        return "ranking";
    }
}