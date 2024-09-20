package com.example.demo.dynamic;

import org.springframework.security.oauth2.client.registration.ClientRegistration;

import java.util.List;
import java.util.Set;

/**
 * This is dummy implementation where we use direct company name everywhere.
 */
public interface IdentityProviderService {

    Set<String> getAllAvailableCompanies();

    List<PerCompanyRegistration> getAllIdpRegistrations();

    List<PerCompanyRegistration> getIdpRegistrationsForCompany(String companyName);

    void upsertIdpRegistration(String companyName, ClientRegistration newRegistrationDetails);

    void deleteAllRegistrationsForCompany(String companyName);

}
