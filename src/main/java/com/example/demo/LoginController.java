package com.example.demo;

import com.example.demo.dynamic.IdentityProviderService;
import com.example.demo.dynamic.PerCompanyRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final IdentityProviderService identityProviderService;

    @GetMapping("/select-company")
    public String selectCompany(Model model) {
        Set<String> companies = identityProviderService.getAllAvailableCompanies();
        model.addAttribute("companies", companies);
        return "select-company";
    }

    @PostMapping("/providers")
    public String login(@RequestParam String companyName, Model model) {
        Iterable<ClientRegistration> clientRegistrations =
                identityProviderService.getIdpRegistrationsForCompany(companyName)
                        .stream().map(PerCompanyRegistration::clientRegistration).toList();
        List<ClientProvider> providers = new ArrayList<>();
        clientRegistrations.forEach(
                registration -> providers.add(new ClientProvider(registration.getClientName(), registration.getRegistrationId())));
        model.addAttribute("providers", providers);
        return "login";
    }

    record ClientProvider(String name, String registrationId) {
    }
}
