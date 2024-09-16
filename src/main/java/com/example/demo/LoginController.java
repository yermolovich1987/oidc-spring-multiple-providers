package com.example.demo;

import com.example.demo.dynamic.IdentityProviderService;
import com.example.demo.dynamic.PerCompanyRegistration;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final IdentityProviderService identityProviderService;

    @GetMapping("/login")
    public String login(Model model) {
        Iterable<ClientRegistration> clientRegistrations = identityProviderService.getAllIdpRegistrations()
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
