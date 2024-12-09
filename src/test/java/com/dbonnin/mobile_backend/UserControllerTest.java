package com.dbonnin.mobile_backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dbonnin.mobile_backend.persistence.User;
import com.dbonnin.mobile_backend.persistence.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Base64;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        // Create the authentication user
        userRepository.save(new User(null, "bob_wilson", 
            passwordEncoder.encode("pass789"), 
            "bob@example.com", 
            "Bob", 
            "Wilson"));
    }

    private HttpHeaders createHeaders(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("pass123");
        user.setEmail("john@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        HttpHeaders headers = createHeaders("bob_wilson", "pass789");
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<User> response = restTemplate.exchange(
            "/users",
            HttpMethod.POST,
            request,
            User.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("john_doe");
    }

    @Test
    void testGetUser() {
        User savedUser = userRepository.save(new User(null, "jane_smith", 
            passwordEncoder.encode("pass456"), 
            "jane@example.com", 
            "Jane", 
            "Smith"));

        HttpHeaders headers = createHeaders("bob_wilson", "pass789");
        
        ResponseEntity<User> response = restTemplate.exchange(
            "/users/" + savedUser.getId(),
            HttpMethod.GET,
            new HttpEntity<>(headers),
            User.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("jane_smith");
    }

    @Test
    void testGetAllUsers() {
        HttpHeaders headers = createHeaders("bob_wilson", "pass789");

        ResponseEntity<List<User>> response = restTemplate.exchange(
            "/users",
            HttpMethod.GET,
            new HttpEntity<>(headers),
            new ParameterizedTypeReference<>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0); // At least the auth user exists
    }

    @Test
    void testUpdateUser() {
        // First create a user to update
        User savedUser = userRepository.save(new User(null, "update_user", 
            passwordEncoder.encode("oldpass"), 
            "old@example.com", 
            "Update", 
            "User"));

        // Create update data
        User updateData = new User();
        updateData.setUsername("updated_user");
        updateData.setPassword("newpass");
        updateData.setEmail("new@example.com");
        updateData.setFirstName("Updated");
        updateData.setLastName("User");

        HttpHeaders headers = createHeaders("bob_wilson", "pass789");
        HttpEntity<User> request = new HttpEntity<>(updateData, headers);

        ResponseEntity<User> response = restTemplate.exchange(
            "/users/" + savedUser.getId(),
            HttpMethod.PUT,
            request,
            User.class
        );

        System.out.println("status: " + response.getStatusCode().toString());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("updated_user");
    }

    @Test
    void testDeleteUser() {
        User savedUser = userRepository.save(new User(null, "delete_user", 
            passwordEncoder.encode("pass"), 
            "delete@example.com", 
            "Delete", 
            "User"));

        HttpHeaders headers = createHeaders("bob_wilson", "pass789");

        ResponseEntity<User> response = restTemplate.exchange(
            "/users/" + savedUser.getId(),
            HttpMethod.DELETE,
            new HttpEntity<>(headers),
            User.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userRepository.findById(savedUser.getId())).isEmpty();
    }
}