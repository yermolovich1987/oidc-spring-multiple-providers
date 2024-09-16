package com.example.demo;

import com.example.demo.dynamic.DynamicOAuth2ClientRegistrationRepository;
import com.example.demo.dynamic.IdentityProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public DynamicOAuth2ClientRegistrationRepository dynamicClientRegistrationRepository(
            @Autowired IdentityProviderService identityProviderService) {
        return new DynamicOAuth2ClientRegistrationRepository(identityProviderService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           DynamicOAuth2ClientRegistrationRepository dynamicOAuth2ClientRegistrationRepository) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/idp/**").permitAll()
                        .anyRequest().authenticated()
                )
                // This is required to execute POST requests without CSRF tokens
                .csrf(AbstractHttpConfigurer::disable)
//                .oauth2Login(Customizer.withDefaults());
                .oauth2Login(oauth2 -> oauth2
                        .clientRegistrationRepository(dynamicOAuth2ClientRegistrationRepository)
                );

        return http.build();
    }
}
