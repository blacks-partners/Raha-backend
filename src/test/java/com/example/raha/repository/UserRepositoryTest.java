package com.example.raha.repository;

import static org.junit.jupiter.api.Assertions.assertNull;

import javax.sql.DataSource;

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
            new ClassPathResource("/01_createTableDemoData.sql")
        );
        populator.execute(detaSource);
    }

    @Test
    void testDelete() {
        repository.delete(1);
        User result = repository.load(1);
        assertNull(result);

    }

    @Test
    void testFindByEmail() {

    }

    @Test
    void testInsert() {

    }

    @Test
    void testLoad() {

    }

    @Test
    void testLoadByEmail() {

    }

    @Test
    void testUpdate() {

    }
}
