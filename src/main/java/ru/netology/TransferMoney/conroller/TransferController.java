package ru.netology.TransferMoney.conroller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.TransferMoney.DTO.CardFromAndCardTo;
import ru.netology.TransferMoney.DTO.Confirmation;
import ru.netology.TransferMoney.Service.TransferService;
import ru.netology.TransferMoney.logger.Logger;

@RestController
public class TransferController {
    TransferService transferService;
    Logger logger = Logger.getInstance();

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public String transferMoney(@Validated @RequestBody CardFromAndCardTo cardFromAndCardTo) {
        logger.log(String.format("Transfer from card %s to %s with amount %d, commission %.2f, successful",
                cardFromAndCardTo.getCardFromNumber(), cardFromAndCardTo.getCardToNumber(),
                cardFromAndCardTo.getAmount().getValue(), (double) cardFromAndCardTo.getAmount().getValue()/100));
        return transferService.getId(cardFromAndCardTo).toString();
    }

    @PostMapping("/confirmOperation")
    public String confirmOperation(@RequestBody Confirmation confirmation) {
        confirmation.setOperationId(transferService.getId());
        confirmation.setCode("12345");
        return "OperationId = " + transferService.confirm(confirmation);
    }

    @GetMapping("/")
    public String get() {
        return "Transfer service is working";
    }
}
