package com.example.SecurityApp.handlers;


import com.example.SecurityApp.entities.User;
import com.example.SecurityApp.services.JWTService;
import com.example.SecurityApp.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
@Builder
public class OAuthenticationSuccess extends SimpleUrlAuthenticationSuccessHandler{

    private final UserService userService;
    private final JWTService jwtService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) token.getPrincipal();

//        logger.info(defaultOAuth2User.getAttributes());
//        logger.info((Object) "User Email: {}");
//        [[Process to find if the user is already present in
//        the db or not if not then load the user and else create new

        String email = defaultOAuth2User.getAttribute("email");
        User user = userService.getUserByEmail(email);

        if(user == null) {

            User newUser = User.UserBuilder.anUser()
                    .builderEmail(email)
                    .builderName(defaultOAuth2User.getAttribute("name")) // Assuming the OAuth2 response has "name"
                    .builderPassword("").build(); // Set empty password as we don't need it for OAuth2 login

            user = userService.save(newUser);
        }
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
//      cookie.setSecure();if we want to set security key  if we have prod env
        response.addCookie(cookie);
        ResponseEntity.ok(cookie);

        String frontEndUrl = "http://localhost:8080/home.htm?token"+accessToken;

        getRedirectStrategy().sendRedirect(request,response,frontEndUrl);



    }
}
