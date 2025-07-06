package ru.netology.TransferMoney.Service;

import org.springframework.stereotype.Service;
import ru.netology.TransferMoney.DTO.CardFromAndCardTo;
import ru.netology.TransferMoney.DTO.Confirmation;
import ru.netology.TransferMoney.exceptions.ConfirmationException;
import ru.netology.TransferMoney.exceptions.InputDataException;
import ru.netology.TransferMoney.logger.Logger;
import ru.netology.TransferMoney.repository.TransferRepository;

import java.time.YearMonth;

@Service
public class TransferService {

    TransferRepository transferRepository;
    Logger logger = Logger.getInstance();

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public Integer getId(CardFromAndCardTo transfer) {
        int operationId = transferRepository.getId();
        if (transfer.getAmount().getValue() <= 0) {
            logger.log(String.format("Transfer from card %s to %s with amount %d, commission %.2f, failed by amount",
                    transfer.getCardFromNumber(), transfer.getCardToNumber(),
                    transfer.getAmount().getValue(), transfer.getAmount().getValue()*0.01));
            throw new InputDataException("Сумма должна быть больше нуля");
        }
        if (transfer.getCardFromNumber().length() != 16) {
            logger.log(String.format("Transfer from card %s to %s with amount %d, commission %f, failed by first card",
                    transfer.getCardFromNumber(), transfer.getCardToNumber(),
                    transfer.getAmount().getValue(), transfer.getAmount().getValue()*0.01));
            throw new InputDataException("Номер карты должен состоять из 16 цифр");
        }
        if (YearMonth.of(2000 + Integer.parseInt(transfer.getCardFromValidTill().substring(3, 5)),
                Integer.parseInt(transfer.getCardFromValidTill().substring(0, 2))).isBefore(YearMonth.now())) {
            logger.log(String.format("Transfer from card %s to %s with amount %d, commission %.2f, failed by valid date",
                    transfer.getCardFromNumber(), transfer.getCardToNumber(),
                    transfer.getAmount().getValue(), transfer.getAmount().getValue()*0.01));
            throw new InputDataException("Истек срок действия карты");
        }
        if (transfer.getCardFromCVV().length() != 3) {
            logger.log(String.format("Transfer from card %s to %s with amount %d, commission %.2f, failed by CVV",
                    transfer.getCardFromNumber(), transfer.getCardToNumber(),
                    transfer.getAmount().getValue(), transfer.getAmount().getValue()*0.01));
            throw new InputDataException("CVV должен состоять из 3 цифр");
        }
        if (transfer.getCardToNumber().length() != 16) {
            logger.log(String.format("Transfer from card %s to %s with amount %d, commission %.2f, failed by second card",
                    transfer.getCardFromNumber(), transfer.getCardToNumber(),
                    transfer.getAmount().getValue(), transfer.getAmount().getValue()*0.01));
            throw new InputDataException("Номер карты должен состоять из 16 цифр");
        } else if (transfer.getCardFromNumber().equals(transfer.getCardToNumber())) {
            logger.log(String.format("Transfer from card %s to %s with amount %d, commission %.2f, failed by equal cards",
                    transfer.getCardFromNumber(), transfer.getCardToNumber(),
                    transfer.getAmount().getValue(), transfer.getAmount().getValue()*0.01));
            throw new InputDataException("Номер карты не может совпадать с номером карты отправителя");
        }
        return operationId;
    }

    public String getId() {
        return String.valueOf(transferRepository.getLastId());
    }

    public String confirm(Confirmation confirmation) {
        if (!confirmation.getCode().equals("12345")) {
            throw new ConfirmationException("Неверный код подтверждения");
        }
        if (confirmation.getOperationId().isEmpty() || confirmation.getCode().isEmpty()) {
            throw new InputDataException("Произошла ошибка при подтверждении операции");
        }
        return confirmation.getOperationId();
    }
}
