package br.com.senior.config.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import br.com.senior.messaging.context.ServiceContextMessageSupplier;
import br.com.senior.messaging.context.ServiceContextMessengerSupplier;
import br.com.senior.messaging.context.functional.MessageSupplier;
import br.com.senior.messaging.context.functional.MessengerSupplier;
import br.com.senior.messaging.context.user.CommonServiceContextUserIdentifier;
import br.com.senior.sdl.user.UserIdentifier;

@Configuration
public class ServiceConfig {
    
    @Primary
    @Bean
    public UserIdentifier userIdentifier() {
        return new CommonServiceContextUserIdentifier();
    }
    
    @Primary
    @Bean
    public MessengerSupplier messengerSupplier() {
        return new ServiceContextMessengerSupplier();
    }
    
    @Primary
    @Bean
    public MessageSupplier messageSupplier() {
        return new ServiceContextMessageSupplier();
    }
}
