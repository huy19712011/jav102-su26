package org.example.jav102su26.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "drinks")
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    Category category;

    // Bill lines that reference this drink (optional back-reference).
    @OneToMany(mappedBy = "drink")
    Set<BillDrink> billDrinks = new HashSet<>();

    @Column(nullable = false)
    String name;

    String description;
    String image;

    @Column(precision = 12, scale = 2)
    BigDecimal price;

    boolean active;
}
