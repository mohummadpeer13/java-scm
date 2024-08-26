package com.example.demo.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Providers;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuth2 Login Success !!!");

        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        
        // recupere nom du provider (si google, github ....)
        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        // oauthUser.getAttributes().forEach((key, value) -> {
        // logger.info(key + " : " + value);
        // });
        // logger.info(oauthUser.getAuthorities().toString());

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of("ROLE_USER"));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("dummy");

        // si google
        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setUserImage(oauthUser.getAttribute("picture").toString());
            user.setPseudo(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setDescription("This account is created using google.");
        } 

        User userExist = this.userRepository.findByEmail(user.getEmail()).orElse(null);
        if (userExist == null) {
            this.userRepository.save(user);
            logger.info("User OAuth2 " + user.getEmail() + " saved in database ");
        } else {
            logger.info("OAuthAuthenicationSuccessHandler: Oauth2 User allready exist !!!");
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }

}
