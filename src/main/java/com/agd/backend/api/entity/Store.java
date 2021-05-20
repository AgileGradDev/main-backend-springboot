package com.agd.backend.api.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(length = 36)
    private UUID id;

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition="TEXT")
    private String description;

    @Column(length = 100, nullable = false)
    private String openingHours;

    @Column(length = 100, nullable = false)
    private String closedOn;

    @Column(length = 100, nullable = false)
    private String phone;

    @Column(nullable = false)
    private float rating;

    @Column(length = 50, nullable = false)
    private String location;
}
