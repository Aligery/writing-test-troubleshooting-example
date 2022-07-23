package org.example;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class   AbstractContainerTest {

    public static final PostgreSQLContainer<?> PGSQL_CONTAINER;

    static {
        PGSQL_CONTAINER = new PostgreSQLContainer<>("postgres:14.1")
                .withUsername("root")
                .withPassword("root")
                .withDatabaseName("test");

        PGSQL_CONTAINER.start();
    }


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", PGSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", PGSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", PGSQL_CONTAINER::getPassword);
    }

}
