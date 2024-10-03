package com.tallerwebi.dominio;

import javax.persistence.*;


@Entity
public class ProyectoDeInversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
