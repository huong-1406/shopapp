package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity//dùng để ánh xạ
@Table(name = "social_accounts")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount {

    @Id//khoá chính
    @GeneratedValue(strategy = GenerationType.IDENTITY)//database tự đọng tăng giá trị thêm 1
    private Long id;

    @Column(name="provider", nullable = false,length = 20)
    private String provider;

    @Column(name="provider_id", nullable = false,length = 50)
    private String providerId;

    @Column(name="name", length = 150)
    private String name;

    @Column(name="email", length = 150)
    private String email;

}
