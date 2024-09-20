package com.example.demo.dynamic.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Data
@NoArgsConstructor
public class IdpDetails {
    private String companyName;
    private String issuerUri;
    private String clientName;
    private String clientId;
    private String clientSecret;
    private ClientAuthenticationMethod clientAuthenticationMethod;

}
