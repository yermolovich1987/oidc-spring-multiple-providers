package com.example.demo.dynamic;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SimpleInMemoryIdentityProviderService implements IdentityProviderService {
    private final Map<String, List<PerCompanyRegistration>> clientRegistrations = new ConcurrentHashMap<>();


    @Override
    public List<PerCompanyRegistration> getAllIdpRegistrations() {
        return clientRegistrations.values().stream().flatMap(Collection::stream).toList();
    }

    @Override
    public List<PerCompanyRegistration> getIdpRegistrationsForCompany(String companyName) {
        return clientRegistrations.get(companyName);
    }

    @Override
    public void upsertIdpRegistration(String companyName, ClientRegistration newRegistrationDetails) {
        clientRegistrations.compute(companyName, (suppliedName, oldValue) -> {
            if (CollectionUtils.isEmpty(oldValue)) {
                return new CopyOnWriteArrayList<>(List.of(new PerCompanyRegistration(companyName, newRegistrationDetails)));
            }

            oldValue.add(new PerCompanyRegistration(companyName, newRegistrationDetails));
            return oldValue;
        });
    }

    @Override
    public void deleteAllRegistrationsForCompany(String companyName) {
        // TODO How to remove just a particular registration?
        clientRegistrations.remove(companyName);
    }
}
