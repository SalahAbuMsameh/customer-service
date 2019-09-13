package com.apisoft.customer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Customer microservice configurations.
 *
 * @author Salah Abu Msameh
 */
public class CustomerMsConfigs {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    /**
     * register new java {@link java.time.LocalDate} & {@link java.time.LocalDateTime} serializers/deserializers.
     */
    @PostConstruct
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }
}
