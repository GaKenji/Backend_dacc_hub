package com.dacchub.backend.game.entity;

import com.dacchub.backend.common.enums.Engine;
import com.dacchub.backend.common.enums.GameMode;
import com.dacchub.backend.common.enums.License;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity //Define que essa classe será uma tabela em um banco de dados
@Table(name = "games") //Define o nome da tabela

//Anotações para gerar Getters e Setters Automaticamente
@Getter
@Setter

//Anotações para setar construtores
@NoArgsConstructor
@AllArgsConstructor


public class Game {
    @Id //Define a coluna id como chave primária
    @GeneratedValue(strategy = GenerationType.UUID) //Define o autoIncremento para a chave primária
    private UUID id;

    private String title;

    private String tagline;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String bannerUrl;

    private String repositoryUrl;

    private String downloadUrl;

    @Enumerated(EnumType.STRING)
    private Engine engine;

    private String version;

    private String fileSize;

    private LocalDate releaseDate;

    private LocalDateTime updatedAt;

    @Column(columnDefinition = "TEXT")
    private String minimumRequirements;

    private String academicContext;

    @Enumerated(EnumType.STRING)
    private License license;

    @ElementCollection
    private List<String> supportedLanguages;

    @ElementCollection
    private List<String> genres;

    @Enumerated(EnumType.STRING)
    private GameMode gameMode;

    private int downloadCount;

    private int viewCount;

    private int likeCount;

    private boolean featured;

    private LocalDateTime createdAt;

    //Chaves estrangeiras a serem implementadas, esperar modelagem de usuário, instituição e cursos
    private UUID userId;
    private UUID institutionId;
    private UUID courseId;
}
