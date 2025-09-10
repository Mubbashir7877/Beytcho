package com.beytcho.Beytcho.Entities;


import jakarta.persistence.*;
import lombok.Data;
import com.beytcho.Beytcho.Entities.Product;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "categories")

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> productList;

    @Column(name = "created_at")
    private final LocalDateTime created = LocalDateTime.now();

}
