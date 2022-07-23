package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.rest.AccountRest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void initDB(@Autowired DataSource dataSource) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(con, new ClassPathResource("/sql/drop_data.sql"));
            ScriptUtils.executeSqlScript(con, new ClassPathResource("/sql/test_data.sql"));
        }
    }

    @Test
    public void successGetAll() throws Exception {
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/account"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var content = result.getResponse().getContentAsString();
        var resultArray = objectMapper.readValue(content, Long[].class);
        Assertions.assertEquals(1, resultArray.length);
    }

    @Test
    public void successGet() throws Exception {
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/account/{accountId}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    public void successPost() throws Exception {
        var request = new AccountRest();
        request.setSumMoneyRub(1000L);
        request.setUserId(44312L);
        var result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/account/")
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

    @Test
    public void unSuccessPost() throws Exception {
        var request = new AccountRest();
        request.setSumMoneyRub(1000L);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/account/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void unSuccessPostWithEmptyBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/account/"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

}
