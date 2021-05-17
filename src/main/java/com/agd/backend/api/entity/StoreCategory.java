package com.agd.backend.api.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class StoreCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    public StoreCategory(String name) {
        this.name = name;
    }
}