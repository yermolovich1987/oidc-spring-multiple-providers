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
                .clientId(idpDetails.getClientId())
                .clientSecret(idpDetails.getClientSecret())
                .clientAuthenticationMethod(idpDetails.getClientAuthenticationMethod())
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

//    @PostConstruct
//    void postConstruct() {
//        var registration = ClientRegistrations
//                .fromOidcIssuerLocation("https://auth.pingone.eu/fd252206-ed6e-4760-b0d6-ec4fcbeb040e/as")
//                .clientName("testDynamicPing")
//                .clientId("df78ef33-896d-411d-bd96-1d7580e4af3d")
//                .clientSecret("iwIcjJk69J3OEftWDK0HuHMrd44H8jlGcUoyZWvbQmGCuU8widWo9vueLln6GGg.")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
//                .build();
//
//        registerNewIdp();
//    }
}
