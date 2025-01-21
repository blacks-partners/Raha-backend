package com.example.raha.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DynamicDataSourceConfig {
    @Autowired
    private Environment environment;

    @SuppressWarnings("null")
	@Bean
    public DataSource dataSource() {
        String url;
        if (isRunningInDocker()) {
            url = environment.getProperty("spring.datasource.docker-url");
        } else {
            url = environment.getProperty("spring.datasource.local-url");
        }

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(url);
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

    private boolean isRunningInDocker() {
        return System.getenv("DOCKER_ENV") != null;
    }

}
