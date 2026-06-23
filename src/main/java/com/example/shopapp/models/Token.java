package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity//dùng để ánh xạ
@Table(name = "tokens")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {


    @Id//khoá chính
    @GeneratedValue(strategy = GenerationType.IDENTITY)//database tự đọng tăng giá trị thêm 1
    private Long id;


    @Column(name = "token",length = 255)
    private String token;

    @Column(name = "token_type",length = 50)
    private String tokenType;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    private boolean revoked;
    private  boolean expired;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;//tham chiếu đến thực thể user trong model
}
