package com.dacchub.backend.game.entity;

import com.dacchub.backend.common.enums.TipoGameMidia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity//Define que essa classe será uma tabela no banco de dados
@Table(name = "game-midia")//Define o nome da tabela

//Geram Getters e Setters Automaticamente
@Getter
@Setter

//Seta construtores
@AllArgsConstructor
@NoArgsConstructor

public class GameMidia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; //Identificador único do game midia

    private String url; //URL da midia

    @Enumerated(EnumType.STRING)
    private TipoGameMidia tipo; //screenshot | trailer | gif

    private int ordem; //Posição na galeria (para ordenação)

    private String altText; //Texto alternativo para acessibilidade (nullable)

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game gameId; //Jogo associado

    private Boolean isHighLight; //I
    // ndica se é mídia em destaque
}
