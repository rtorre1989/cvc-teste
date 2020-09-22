package br.com.cvc.teste.cvcteste.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "broker.hotels", ignoreUnknownFields = true)
public class BrokerProperties {

    private String url;
    private String availCity;
    private String details;
    
}
