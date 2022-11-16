package com.golfzonaca.backoffice.domain.type;

public enum TrxType {
    Deposit("보증금"), Balance("잔금"), FullPayment("전체 결제");
    private final String description;

    TrxType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}