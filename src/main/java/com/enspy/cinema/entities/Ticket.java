package com.enspy.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(length = 25)
    private String nomClient;
    private double prix;
    @Column(unique = false)
    private int codePayement;
    private boolean reserve;
    @ManyToOne
    private Place place;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Projection projection;

}
