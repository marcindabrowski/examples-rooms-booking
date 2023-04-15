package com.github.marcindabrowski.example.roomsbooking.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.marcindabrowski.example.roomsbooking.domain.model.Amount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JsonConfiguration {

  @Bean
  SimpleModule jacksonModuleWithCustomDeserializers() {
    SimpleModule module = new SimpleModule("ServiceDeserializerModule");
    module.addDeserializer(Amount.class, new AmountDeserializer());
    return module;
  }
}
