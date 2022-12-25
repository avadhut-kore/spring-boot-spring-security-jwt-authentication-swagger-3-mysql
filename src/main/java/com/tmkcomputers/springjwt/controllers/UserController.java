package com.tmkcomputers.springjwt.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmkcomputers.springjwt.exception.ResourceNotFoundException;
import com.tmkcomputers.springjwt.models.User;
import com.tmkcomputers.springjwt.payload.request.ChangePasswordRequest;
import com.tmkcomputers.springjwt.payload.response.MessageResponse;
import com.tmkcomputers.springjwt.payload.response.UserProfileResponse;
import com.tmkcomputers.springjwt.repository.UserRepository;
import com.tmkcomputers.springjwt.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder encoder;

  @PostMapping("/change-password")
  public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(changePasswordRequest.getUsername(),
            changePasswordRequest.getCurrentPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    User user = userRepository.findByUsername(changePasswordRequest.getUsername())
        .orElseThrow(() -> new ResourceNotFoundException("User", "username", changePasswordRequest.getUsername()));

    user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Password changed successfully!"));
  }

  // Get user profile
  @GetMapping("/profile")
  public ResponseEntity<?> getUserProfile() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    return ResponseEntity.ok(new UserProfileResponse(
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        roles));
  }
}
