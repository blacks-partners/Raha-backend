package com.example.raha.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.Transactional;

import com.example.raha.domain.User;

@SpringBootTest
@Transactional
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository repository;

    @BeforeAll
    static void testBeforeAll(@Autowired DataSource detaSource){
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(
            new ClassPathResource("testData.sql"));
        populator.execute(detaSource);
    }

    @Test
    void testDelete() {
        // repository.delete(1);
        // User result = repository.load(1);
        // assertNull(result);

    }

    @Test
    void testFindByEmail() {

    }

    @Test
    void testInsert() {

    }

    @Test
    void testLoad() {
        User result = repository.load(1);
        assertEquals(result.getEmail(), "demo_user@example.com");
    }

    @Test
    void testLoadByEmail() {
        User result = repository.findByEmail("demo_user@example.com");
        User user = repository.load(1);
        assertEquals(result.getName(), user.getName());
    }

    @Test
    void testUpdate() {
        

    }

    @AfterAll
    static void testAfterAll(@Autowired DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
        new ClassPathResource("/testData.sql"));
        populator.execute(dataSource);
    }
}
