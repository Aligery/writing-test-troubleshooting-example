package org.example.correct;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.AbstractAccountControllerTest;
import org.example.domain.entity.AccountEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GetAccountControllerTest extends AbstractAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private AccountGenerator accountGenerator;


    @Test
    public void successGetAll() throws Exception {
        var generatedEntity = List.of(accountGenerator.generateRandom(), accountGenerator.generateRandom(), accountGenerator.generateRandom());
        var generatedListId = generatedEntity.stream().map(AccountEntity::getId).collect(Collectors.toList());

        var result = mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_URI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var content = result.getResponse().getContentAsString();
        var resultArray = objectMapper.readValue(content, Long[].class);
        var resultList = Arrays.asList(resultArray);
        Assertions.assertTrue(resultList.containsAll(generatedListId));
    }

    @Test
    public void successGet() throws Exception {
        var entity = accountGenerator.generateRandom();
        var result = mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_ID_URI, entity.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }


}
