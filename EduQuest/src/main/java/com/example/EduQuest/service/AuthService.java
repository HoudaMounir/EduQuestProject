package com.example.EduQuest.service;

import com.example.EduQuest.dto.LoginRequest;
import com.example.EduQuest.dto.RegisterRequest;
import com.example.EduQuest.model.User;
import com.example.EduQuest.repository.UserRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Service
public class AuthService {
    private final Keycloak keycloak;
    @Autowired
    private final UserRepository userRepository;

    public AuthService(Keycloak keycloak, UserRepository userRepository) {
        this.keycloak = keycloak;
        this.userRepository = userRepository;
    }
    Logger logger = LoggerFactory.getLogger(AuthService.class);

    public void registerUser(RegisterRequest registerRequest) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(registerRequest.getPassword());

        user.setCredentials(Collections.singletonList(credential));

        Response response = keycloak.realm("evaluation-platform")
                .users()
                .create(user);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user in Keycloak");
        }

        String userId = response.getLocation().getPath().replaceAll(".*/(.*)$", "$1");
        UserResource userResource = keycloak.realm("evaluation-platform").users().get(userId);
        UserRepresentation keycloakUser = userResource.toRepresentation();



        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());

        try {
        User newUser = new User();
        newUser.setUsername(keycloakUser.getUsername());
        newUser.setEmail(keycloakUser.getEmail());
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setPassword(hashedPassword);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

            logger.info("Saving user to database: {}", newUser);
            userRepository.save(newUser);
            logger.info("User saved successfully");
        } catch (Exception e) {
            logger.error("Failed to save user in database", e);
            throw new RuntimeException("Failed to save user in database", e);
        }
    }

    public String loginUser(LoginRequest loginRequest) {
        RestTemplate restTemplate = new RestTemplate();

        String tokenUrl = "http://localhost:8080/realms/evaluation-platform/protocol/openid-connect/token";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", "spring-boot-backend");
        body.add("client_secret", "jS7Uiw0lo6enFvGaec47qAt1z7FiKRmM");
        body.add("grant_type", "password");
        body.add("username", loginRequest.getUsername());
        body.add("password", loginRequest.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody().get("access_token").toString();
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid credentials: " + e.getMessage());
        }

        throw new RuntimeException("Login failed.");
    }

    public User getUserFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        // Vérifier si le principal est un Jwt
        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();

            String username = jwt.getClaim("preferred_username");
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found in database"));
        } else {
            throw new RuntimeException("Unexpected principal type: " + authentication.getPrincipal().getClass());
        }
    }
}
