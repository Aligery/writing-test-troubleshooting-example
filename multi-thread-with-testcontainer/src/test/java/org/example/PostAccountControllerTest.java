package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.rest.AccountRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PostAccountControllerTest extends AbstractAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void successPost() throws Exception {
        //Пример, поставил 1000 чтобы точно поймать проблему с многопоточкой
        var request = new AccountRest();
        request.setSumMoneyRub(1000L);
        request.setUserId(44312L);
        for (int i = 0; i < 1000; i++) {
            var result = mockMvc.perform(
                            MockMvcRequestBuilders.post(ACCOUNT_URI)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            var content = result.getResponse().getContentAsString();
            AccountRest accountRest = objectMapper.readValue(content, AccountRest.class);
            Assertions.assertEquals(request.getSumMoneyRub(), accountRest.getSumMoneyRub());
            Assertions.assertEquals(request.getUserId(), accountRest.getUserId());
        }
    }

    @Test
    @Disabled
    public void unSuccessPost() throws Exception {
        var request = new AccountRest();
        request.setSumMoneyRub(1000L);
        mockMvc.perform(
                        MockMvcRequestBuilders.post(ACCOUNT_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Disabled
    public void unSuccessPostWithEmptyBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URI))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }
}
