package com.novacart.NovaCart.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "addresses")


public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String street;
    private String city;
    private String state;
    private String zip;
    private String country;

    @OneToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name ="created_at")
    private final LocalDateTime created = LocalDateTime.now();
}
