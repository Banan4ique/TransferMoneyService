package ru.netology.TransferMoney.service;

import org.springframework.stereotype.Service;
import ru.netology.TransferMoney.DTO.CardFromAndCardTo;
import ru.netology.TransferMoney.DTO.Confirmation;
import ru.netology.TransferMoney.exception.InputDataException;
import ru.netology.TransferMoney.exception.TransferException;
import ru.netology.TransferMoney.logger.Logger;
import ru.netology.TransferMoney.repository.TransferRepository;

import java.time.YearMonth;

@Service
public class TransferService {

    private static final int CARD_LENGTH = 16;
    private static final int CVV_LENGTH = 3;
    private static final int AMOUNT_MIN = 0;
    private static final String CARD_NUMBER_MESSAGE = "Номер карты должен состоять из 16 цифр";
    private static final String CARD_EQUAL_MESSAGE = "Номер карты не может совпадать с номером карты отправителя";
    private static final String CVV_MESSAGE = "CVV должен состоять из 3 цифр";
    private static final String INPUT_DATA_MESSAGE = "Произошла ошибка при подтверждении операции";
    private static final String AMOUNT_MESSAGE = "Сумма должна быть больше нуля";
    private static final String DATE_MESSAGE = "Дата действия истекла";

    private final TransferRepository transferRepository;
    private final Logger logger = Logger.getInstance();

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public Integer getId(CardFromAndCardTo transfer) {
        isValidAmount(transfer.getAmount().getValue());
        isValidCardNumber(transfer.getCardFromNumber().length());
        isValidCardNumber(transfer.getCardToNumber().length());
        isValidDate(YearMonth.of(2000 + Integer.parseInt(transfer.getCardFromValidTill().substring(3, 5)),
                Integer.parseInt(transfer.getCardFromValidTill().substring(0, 2))));
        isValidCVV(transfer.getCardFromCVV().length());
        isEqualCards(transfer.getCardFromNumber(), transfer.getCardToNumber());
        logger.log("Transfer successful");
        return transferRepository.getId();
    }

    public String getId() {
        return String.valueOf(transferRepository.getLastId());
    }

    public String confirm(Confirmation confirmation) {
        if (confirmation.getOperationId().isEmpty() || confirmation.getCode().isEmpty()) {
            throw new TransferException(INPUT_DATA_MESSAGE);
        }
        return confirmation.getOperationId();
    }

    public void isValidAmount(int amount) {
        if (amount <= AMOUNT_MIN) {
            logger.log("Transfer failed by amount");
            throw new InputDataException(AMOUNT_MESSAGE);
        }
    }

    public void isValidCardNumber(int length) {
        if (length != CARD_LENGTH) {
            logger.log("Transfer failed by card number");
            throw new InputDataException(CARD_NUMBER_MESSAGE);
        }
    }

    public void isValidCVV(int length) {
        if (length != CVV_LENGTH) {
            logger.log("Transfer failed by CVV");
            throw new InputDataException(CVV_MESSAGE);
        }
    }

    public void isValidDate(YearMonth date) {
        if (date.isBefore(YearMonth.now())) {
            logger.log("Transfer failed by date");
            throw new InputDataException(DATE_MESSAGE);
        }
    }

    public void isEqualCards(String cardFrom, String cardTo) {
        if (cardFrom.equals(cardTo)) {
            logger.log("Transfer failed by equal cards");
            throw new InputDataException(CARD_EQUAL_MESSAGE);
        }
    }
}
