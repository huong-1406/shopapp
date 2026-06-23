package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder//Nó là công cụ của thư viện Lombok, chỉ hỗ trợ cú pháp viết code Java nhanh hơn.
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("phone_Number")
    private String phoneNumber;



    @NotNull(message = "password cannot be blank")
    private String password;
}
