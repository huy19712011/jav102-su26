package org.example.jav102su26.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;   // store a hash, never the plaintext password

    String fullName;
    String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;

    boolean active;
}
