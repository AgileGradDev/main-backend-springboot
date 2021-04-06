package com.example.demo.api.entity;


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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @Column(length = 20, nullable = false)
    private String user_name;
    @Column(length = 13, nullable = false)
    private String user_phonenumber;
    @Column(length = 50, nullable = false)
    private String user_email;
    @Column(nullable = false)
    private int user_age;
    @Column(nullable = false)
    private char user_sex;
    @Column(length = 100)
    private String provider;

}
