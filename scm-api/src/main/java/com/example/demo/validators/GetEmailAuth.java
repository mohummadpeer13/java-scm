package com.example.demo.validators;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class GetEmailAuth {
    public static String getEmailForAuth(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {

            var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var oauth2User = (DefaultOAuth2User) authentication.getPrincipal();

            // recupere nom du provider (si google, github ....)
            String clientId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
            String username = "";
            if (clientId.equalsIgnoreCase("google")) {
                username = oauth2User.getAttribute("email");
            }
            return username;
        } else {
            return authentication.getName();
        }

    }
}
