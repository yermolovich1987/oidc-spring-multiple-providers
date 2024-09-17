package com.example.demo.dynamic;

import com.example.demo.dynamic.dto.IdpDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("idp")
@RequiredArgsConstructor
public class ClientRegistrationController {
    private final IdentityProviderService identityProviderService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    void registerNewIdp(@RequestBody IdpDetails idpDetails) {
        var registration = ClientRegistrations
                .fromOidcIssuerLocation(idpDetails.getIssuerUri())
                .clientName(idpDetails.getClientName())
                .registrationId(idpDetails.getClientName())
                .clientId(idpDetails.getClientId())
                .clientSecret(idpDetails.getClientSecret())
                .clientAuthenticationMethod(idpDetails.getClientAuthenticationMethod())
                .scope(Set.of("openid", "profile", "email"))
                .build();


        identityProviderService.upsertIdpRegistration(idpDetails.getCompanyName(), registration);
    }

    @PostMapping(path = "/batch", consumes = "application/json", produces = "application/json")
    void registerBatchOfIdps(@RequestBody List<IdpDetails> idpDetailList) {
        idpDetailList.forEach(this::registerNewIdp);
    }

    @GetMapping(produces = "application/json")
    List<PerCompanyRegistration> getAllRegisteredIdps() {
        return identityProviderService.getAllIdpRegistrations();
    }
}
