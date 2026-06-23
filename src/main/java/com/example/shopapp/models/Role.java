package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity//dùng để ánh xạ
@Table(name = "roles")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)

    private String name;

}
