package ru.netology.TransferMoney.exceptions;

public class TransferException extends RuntimeException {
    public TransferException(String message) {
        super(message);
    }
}
