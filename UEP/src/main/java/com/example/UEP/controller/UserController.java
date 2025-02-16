package com.example.UEP.controller;

import com.example.UEP.dto.UserProfileDTO;
import com.example.UEP.model.User;
import com.example.UEP.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public ResponseEntity<UserProfileDTO> getProfile(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.badRequest().build();
        }

        // Extract data from OAuth2User
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");
        String pictureUrl = principal.getAttribute("picture");

        // Find existing profile or create new one
        User userProfile = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newProfile = new User();
                    newProfile.setEmail(email);
                    newProfile.setName(name);
                    newProfile.setPicture(pictureUrl);
                    return userRepository.save(newProfile);
                });

        // Convert to DTO
        UserProfileDTO dto = new UserProfileDTO(
                userProfile.getId().toString(),
                userProfile.getName(),
                userProfile.getEmail(),
                userProfile.getPicture(),
                userProfile.getCollegeName(),
                userProfile.getExperience()
            );
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/user")
    public ResponseEntity<UserProfileDTO> updateProfile(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestBody UserProfileDTO updateRequest) {

        if (principal == null) {
            return ResponseEntity.badRequest().build();
        }

        String email = principal.getAttribute("email");

        User userProfile = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newProfile = new User();
                    newProfile.setEmail(email);
                    newProfile.setName(principal.getAttribute("name"));
                    newProfile.setPicture(principal.getAttribute("picture"));
                    return newProfile;
                });

        userProfile.setCollegeName(updateRequest.collegeName());
        userProfile.setExperience(updateRequest.experience());

        userProfile = userRepository.save(userProfile);

        UserProfileDTO response = new UserProfileDTO(
                userProfile.getId().toString(),
        userProfile.getEmail(),
        userProfile.getName(),
        userProfile.getPicture(),
        userProfile.getCollegeName(),
        userProfile.getExperience()
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        SecurityContextHolder.clearContext();
        request.getSession().invalidate(); // Destroy session
        response.setStatus(HttpServletResponse.SC_OK);
    }
}