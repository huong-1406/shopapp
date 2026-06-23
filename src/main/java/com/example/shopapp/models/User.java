package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity//dùng để ánh xạ
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    @Id//khoá chính
    @GeneratedValue(strategy = GenerationType.IDENTITY)//database tự đọng tăng giá trị thêm 1
    private Long id;


    @Column(name = "fullname",length = 100)
    private String fullName;

    @Column(name = "phone_number",length = 10,nullable = false)
    private String phoneNumber;

    @Column(name = "address",length = 200)
    private String address;

    @Column(name = "password",length = 200)
    private String password;

    private boolean active;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_account_id")
    private int facebookAccountId;

    @Column(name = "google_account_id")
    private int googleAccountId;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private com.example.shopapp.models.Role role;

}
