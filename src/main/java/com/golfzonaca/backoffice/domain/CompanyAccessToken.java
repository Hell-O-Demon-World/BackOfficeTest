package com.golfzonaca.backoffice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "COMPANY_ACCESS_TOKEN")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CompanyAccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", unique = true, nullable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Company company;
    @Column(name = "ENCODED_TOKEN", nullable = false)
    private String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
