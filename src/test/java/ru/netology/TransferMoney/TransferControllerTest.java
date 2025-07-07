package ru.netology.TransferMoney;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.netology.TransferMoney.Service.TransferService;
import ru.netology.TransferMoney.conroller.TransferController;

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
                "    \"code\": \"12345\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/confirmOperation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}
