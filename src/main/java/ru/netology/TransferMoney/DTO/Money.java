package ru.netology.TransferMoney.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Money {
    @NotBlank
    @Pattern(regexp = "^[1-9]+[0-9].$")
    private Integer value;
    @NotBlank
    private String currency;

    public Money(Integer value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Integer getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }
}
