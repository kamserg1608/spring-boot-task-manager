package org.example.taskmanager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/config/email.properties")
@Getter
public class EmailProperties {

    @Value("${email}")
    private String email;

    @Value("${password}")
    private String password;

}