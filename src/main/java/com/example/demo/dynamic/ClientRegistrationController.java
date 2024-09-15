package com.example.demo.dynamic;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("idp")
@RequiredArgsConstructor
public class ClientRegistrationController {
    private final IdentityProviderService identityProviderService;

    @PostMapping
    void registerNewIdp() {
//        ClientRegistration clientRegistration = ClientRegistration
//                .withRegistrationId(UUID.randomUUID().toString())
//                .clientName("testPing")
//                .build();
        var registration = ClientRegistrations
                .fromOidcIssuerLocation("https://auth.pingone.eu/fd252206-ed6e-4760-b0d6-ec4fcbeb040e/as")
                .clientName("testDynamicPing")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .build();


        identityProviderService.upsertIdpRegistration("testCompany", registration);
    }

    @PostConstruct
    void postConstruct() {
        registerNewIdp();
    }
}
