package com.dacchub.backend.game.entity;

import com.dacchub.backend.common.enums.Engine;
import com.dacchub.backend.common.enums.GameMode;
import com.dacchub.backend.common.enums.License;
import com.dacchub.backend.course.entity.Course;
import com.dacchub.backend.institution.entity.Institution;
import com.dacchub.backend.user.entity.User;
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
    private UUID id; //Identificador único

    private String title; //Título do jogo

    private String tagline; //Descrição curta

    @Column(columnDefinition = "TEXT")
    private String description; //Descrição completa com formatação

    private String bannerUrl; //URL da arte/banner principal (16:9 ou 2:3)

    private String repositoryUrl; //Link GitHub/GitLab

    private String downloadUrl; //URL do arquivo de download

    @Enumerated(EnumType.STRING)
    private Engine engine; //Unity | Godot | Unreal | pygame | LibGDX | outro

    private String version; //Versão atual (ex.: 1.0.2

    private String fileSize; //Tamanho do arquivo (ex.: 45 MB)

    private LocalDate releaseDate; //Data de lançamento

    private LocalDateTime updatedAt; //Data da última atualização

    @Column(columnDefinition = "TEXT")
    private String minimumRequirements; //Requisitos mínimos de sistema (nullable)

    private String academicContext; //Disciplina / semestre / tipo de projeto

    @Enumerated(EnumType.STRING)
    private License license; //MIT | GPL | Apache | CC-BY | CC-BY-SA | outro

    @ElementCollection
    private List<String> supportedLanguages; //Idiomas suportados pelo jogo

    @ElementCollection
    private List<String> genres; //Tags de gênero (Plataforma 2D, Puzzle, RPG…)

    @Enumerated(EnumType.STRING)
    private GameMode gameMode; //singleplayer | multiplayer | ambos

    private int downloadCount; //Contador público de downloads

    private int viewCount; //Contador de visualizações da página

    private int likeCount; //Contador de curtidas

    private boolean featured; //Curadoria manual de destaque

    private LocalDateTime createdAt; //Data de criação

    @ManyToOne//Seta uma referencia para a entidade user
    @JoinColumn(name = "user_id")//Seta nome da coluna que armazenará a chave primária de outra tabela
    private User userId; //Desenvolvedor responsável

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institutionId; //Derivado via usuario, denormalizado para queries rápidas

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course courseId; //Curso associado
}
