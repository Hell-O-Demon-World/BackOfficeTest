package com.golfzonaca.backoffice.web.controller;

import com.golfzonaca.backoffice.auth.token.JwtRepository;
import com.golfzonaca.backoffice.web.form.login.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {
    @GetMapping("/signin")
    public String loginForm(@ModelAttribute LoginForm loginForm, Model model) {
        model.addAttribute(loginForm);
        return "/login/loginForm";
    }

}
