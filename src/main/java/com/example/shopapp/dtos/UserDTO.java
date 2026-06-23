package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Builder//Nó là công cụ của thư viện Lombok, chỉ hỗ trợ cú pháp viết code Java nhanh hơn.
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UserDTO {//UserDTO được tạo ra chỉ chứa đúng các trường mà Frontend gửi lên, giúp code chạy nhẹ hơn, không bị thừa thãi dữ liệu
    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("phone_Number")
    private String phoneNumber;

    private String address;


    @NotNull(message = "password cannot be blank")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;


    @NotNull(message = "role_id is required")
    @JsonProperty("role_id")
    private long roleId;




}
