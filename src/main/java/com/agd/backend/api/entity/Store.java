package com.agd.backend.api.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(length = 50, nullable = false)
    private String opening_hours;

    @Column(nullable = false)
    private float rating;

    @Column(length = 50, nullable = false)
    private String location;

}
