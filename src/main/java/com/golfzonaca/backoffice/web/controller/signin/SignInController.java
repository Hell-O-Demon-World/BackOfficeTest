package com.golfzonaca.backoffice.web.controller.signin;

import com.golfzonaca.backoffice.domain.Company;
import com.golfzonaca.backoffice.service.company.CompanyService;
import com.golfzonaca.backoffice.web.controller.signin.dto.SignInDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SignInController {

    private final CompanyService companyService;

    @GetMapping("/signin")
    public String loginForm(@ModelAttribute SignInDto signInDto, Model model) {
        model.addAttribute(signInDto);
        return "login/loginForm";
    }

    @ResponseBody
    @PostMapping("findCompany")
    public String findCompany(@RequestBody String loginId) {
        Company company = companyService.findByLoginId(loginId);
        return "ok";
    }

}
