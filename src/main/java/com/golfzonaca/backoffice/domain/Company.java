package com.golfzonaca.backoffice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private Long id; //업체식별번호
    private String companyLoginId; // 업체의 백오피스 아이디
    private String companyPw;
    private String companyName;
    private String companyTel;
    private String companyRegNum;
    private String companyRepName;
    private Long addressId;

    public Company(String companyLoginId, String companyPw, String companyName, String companyTel, String companyRegNum, String companyRepName, Long addressId) {
        this.companyLoginId = companyLoginId;
        this.companyPw = companyPw;
        this.companyName = companyName;
        this.companyTel = companyTel;
        this.companyRegNum = companyRegNum;
        this.companyRepName = companyRepName;
        this.addressId = addressId;
    }
}
