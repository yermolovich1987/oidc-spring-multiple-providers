package com.example.demo;

import com.example.demo.dynamic.DynamicOAuth2ClientRegistrationRepository;
import com.example.demo.dynamic.IdentityProviderService;
import com.example.demo.dynamic.SimpleInMemoryIdentityProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.function.Consumer;

@Configuration
public class SecurityConfiguration {

//    @Autowired
//    private ClientRegistrationRepository clientRegistrationRepository;

//  @Bean
//  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//    http
//        .authorizeExchange()
//        .anyExchange()
//        .authenticated()
//        .and()
//        .oauth2Login(); // to redirect to oauth2 login page.
//
//    return http.build();
//  }

    @Bean
    public DynamicOAuth2ClientRegistrationRepository dynamicClientRegistrationRepository(
            @Autowired IdentityProviderService identityProviderService) {
        return new DynamicOAuth2ClientRegistrationRepository(identityProviderService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           DynamicOAuth2ClientRegistrationRepository dynamicOAuth2ClientRegistrationRepository) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
//                .oauth2Login(Customizer.withDefaults());
                .oauth2Login(oauth2 -> oauth2
                                .clientRegistrationRepository(dynamicOAuth2ClientRegistrationRepository)
//                        .authorizationEndpoint(authorization -> authorization
//                                .authorizationRequestResolver(
//                                        authorizationRequestResolver(this.clientRegistrationRepository)
//                                )
//                        )
                );

        return http.build();
    }

//    private OAuth2AuthorizationRequestResolver authorizationRequestResolver(
//            ClientRegistrationRepository clientRegistrationRepository) {
//
//        DefaultOAuth2AuthorizationRequestResolver authorizationRequestResolver =
//                new DefaultOAuth2AuthorizationRequestResolver(
//                        clientRegistrationRepository, "/oauth2/authorization");
//        authorizationRequestResolver.setAuthorizationRequestCustomizer(
//                authorizationRequestCustomizer());
//
//        return  authorizationRequestResolver;
//    }
//
//    private Consumer<OAuth2AuthorizationRequest.Builder> authorizationRequestCustomizer() {
//        return customizer -> customizer
//                .additionalParameters(params -> params.put("prompt", "consent"));
//    }
}
