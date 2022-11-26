package com.golfzonaca.backoffice.web.controller.signin.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SignInDto {

    @NotEmpty
    @Size(min = 8, max = 15, message = "아이디의 길이는 8 ~ 15 자리여야 합니다.")
    private String id;
    @NotEmpty
    @Size(min = 8, max = 15, message = "비밀번호의 길이는 8 ~ 15 자리여야 합니다.")
    private String pw;
}