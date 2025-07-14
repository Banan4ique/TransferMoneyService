package ru.netology.TransferMoney;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.netology.TransferMoney.DTO.CardFromAndCardTo;
import ru.netology.TransferMoney.DTO.Confirmation;
import ru.netology.TransferMoney.DTO.Money;
import ru.netology.TransferMoney.conroller.TransferController;
import ru.netology.TransferMoney.exception.InputDataException;
import ru.netology.TransferMoney.exception.TransferException;
import ru.netology.TransferMoney.repository.TransferRepository;
import ru.netology.TransferMoney.service.TransferService;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
public class TransferControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransferService transferService;

    @Test
    public void testPost() throws Exception {
        String json = "{\n" +
                "    \"cardFromNumber\": \"1111111111111111\",\n" +
                "    \"cardFromValidTill\": \"11/33\",\n" +
                "    \"cardFromCVV\": \"111\",\n" +
                "    \"cardToNumber\": \"2222222222222222\",\n" +
                "    \"amount\": {\n" +
                "        \"value\": 100,\n" +
                "        \"currency\": \"rub\"\n" +
                "    }\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testConfirm() throws Exception {
        String json = "{\n" +
                "    \"operationId\": \"1\",\n" +
                "    \"code\": \"0000\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/confirmOperation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testRepositoryGetId() {
        TransferRepository transferRepository = new TransferRepository();
        for (int i = 0; i < 10; i++) {
            int expected = i + 1;
            Assertions.assertEquals(expected, transferRepository.getId());
        }
    }

    @Test
    public void testRepositoryGetLastId() {
        TransferRepository transferRepository = new TransferRepository();
        for (int i = 0; i < 10; i++) {
            int expected = i + 1;
            transferRepository.getId();
            Assertions.assertEquals(expected, transferRepository.getLastId());
        }
    }

    @Test
    public void testTransferServiceGetId() {
        TransferRepository transferRepository = new TransferRepository();
        TransferService transferService = new TransferService(transferRepository);
        for (int i = 0; i < 10; i++) {
            String expected = String.valueOf(i + 1);
            transferRepository.getId();
            Assertions.assertEquals(expected, transferService.getId());
        }
    }

    @ParameterizedTest
    @MethodSource("CardArgumentsProvider")
    public void testTransferServiceGetId(CardFromAndCardTo transfer) {
        TransferRepository transferRepository = new TransferRepository();
        TransferService transferService = new TransferService(transferRepository);
        for (int i = 0; i < 10; i++) {
            int expected = i + 1;
            Assertions.assertEquals(expected, transferService.getId(transfer));
        }
    }

    @Test
    public void testTransferExceptionHandler() {
        TransferService transferService = new TransferService(new TransferRepository());
        Assertions.assertThrows(TransferException.class,
                () -> {transferService.confirm(new Confirmation("", ""));
        });
    }

    @Test
    public void testInputDataExceptionHandler() {
        TransferService transferService = new TransferService(new TransferRepository());
        Assertions.assertThrows(InputDataException.class,
                () -> {transferService.isValidAmount(0);});
    }


    public static Stream<CardFromAndCardTo> CardArgumentsProvider() {
        return Stream.of(
                new CardFromAndCardTo("1111111111111111", "11/33", "111",
                        "2222222222222222", new Money(100, "rub"))
        );
    }
}
