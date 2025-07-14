package ru.netology.TransferMoney.conroller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.TransferMoney.DTO.CardFromAndCardTo;
import ru.netology.TransferMoney.DTO.Confirmation;
import ru.netology.TransferMoney.logger.Logger;
import ru.netology.TransferMoney.service.TransferService;

@RestController
public class TransferController {
    TransferService transferService;
    Logger logger = Logger.getInstance();

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public String transferMoney(@Validated @RequestBody CardFromAndCardTo cardFromAndCardTo) {
        return transferService.getId(cardFromAndCardTo).toString();
    }

    @PostMapping("/confirmOperation")
    public String confirmOperation(@RequestBody Confirmation confirmation) {
        confirmation.setOperationId(transferService.getId());
        return "OperationId = " + transferService.confirm(confirmation);
    }
}
