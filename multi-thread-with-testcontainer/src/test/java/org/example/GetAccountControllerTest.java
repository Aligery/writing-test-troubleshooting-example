package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
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

public class GetAccountControllerTest extends AbstractAccountControllerTest {
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
        //Пример, поставил 1000 чтобы точно поймать проблему с многопоточкой
        for (int i = 0; i < 1000; i++) {
            var result = mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_URI))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            var content = result.getResponse().getContentAsString();
            var resultArray = objectMapper.readValue(content, Long[].class);
            Assertions.assertEquals(1, resultArray.length);
        }
    }

    @Test
    public void successGet() throws Exception {
        var result = mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_ID_URI, "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }


}
