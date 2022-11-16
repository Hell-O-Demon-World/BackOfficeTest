package com.golfzonaca.backoffice.domain;

import com.golfzonaca.backoffice.domain.type.PayType;

public class PaymentInfo {

    private Long id;
    private Long trxId;
    private Long userId;
    private PayType trxType;
    private String trxApiCode;
}
