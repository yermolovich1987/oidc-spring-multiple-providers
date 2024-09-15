package com.example.demo.dynamic;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.util.Iterator;

@RequiredArgsConstructor
public class DynamicOAuth2ClientRegistrationRepository implements ClientRegistrationRepository, Iterable<ClientRegistration> {
    private final IdentityProviderService identityProviderService;

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        // TODO How to extract the proper client registration?
        var registrations = identityProviderService.getIdpRegistrationsForCompany("testCompany");

        return registrations.stream()
                .filter(registration -> registration.clientRegistration().getRegistrationId().equalsIgnoreCase(registrationId))
                .findAny()
                .map(PerCompanyRegistration::clientRegistration)
                .orElseThrow(() -> new IllegalStateException("registration not found"));
    }

    /**
     * @return
     */
    @Override
    public Iterator<ClientRegistration> iterator() {
        return identityProviderService.getAllIdpRegistrations().stream().map(PerCompanyRegistration::clientRegistration).iterator();
    }
}
