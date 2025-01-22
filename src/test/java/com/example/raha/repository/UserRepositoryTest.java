package com.example.raha.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.raha.domain.User;

@SpringBootTest
@Sql("/testData.sql")
@Transactional
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository repository;

    @Test
    void testDelete() {
        // repository.delete(1);
        // verify(this.repository, times(1)).delete(1);
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
}
