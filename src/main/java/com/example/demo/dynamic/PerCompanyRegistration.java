package com.example.demo.dynamic;


import org.springframework.security.oauth2.client.registration.ClientRegistration;

public record PerCompanyRegistration(String tenantId,
                                     ClientRegistration clientRegistration) {
}
