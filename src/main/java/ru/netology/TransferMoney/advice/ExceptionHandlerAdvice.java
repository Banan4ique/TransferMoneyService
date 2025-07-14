package ru.netology.TransferMoney.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.TransferMoney.exception.ConfirmationException;
import ru.netology.TransferMoney.exception.InputDataException;
import ru.netology.TransferMoney.exception.TransferException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(InputDataException.class)
    public ResponseEntity<String> InputDataHandler(InputDataException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<String> TransferHandler(TransferException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConfirmationException.class)
    public ResponseEntity<String> confirmationHandler(ConfirmationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
