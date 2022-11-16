package com.golfzonaca.backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@Import({SecurityConfig.class, MyBatisConfig.class})
//@Configuration
@SpringBootApplication(scanBasePackages = "com.golfzonaca.backoffice")
public class BackOfficeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackOfficeApplication.class, args);
    }
}
