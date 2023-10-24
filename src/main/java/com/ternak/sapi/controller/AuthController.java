package com.ternak.sapi.controller;

import com.ternak.sapi.model.User;
import com.ternak.sapi.payload.ApiResponse;
import com.ternak.sapi.payload.JwtAuthenticationResponse;
import com.ternak.sapi.payload.LoginRequest;
import com.ternak.sapi.payload.SignUpRequest;
import com.ternak.sapi.payload.UserResponse;
import com.ternak.sapi.payload.UserSummary;
import com.ternak.sapi.security.JwtTokenProvider;
import com.ternak.sapi.service.AuthService;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider tokenProvider;


    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
         // Mendapatkan informasi pengguna dari objek authentication
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        UserResponse user = userService.getUserById(userPrincipal.getId());

        // Membuat objek UserSummary dengan informasi pengguna
        UserSummary userSummary = new UserSummary(userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getName(), user.getEmail(), user.getRole(), user.getAvatar());

        // Mengembalikan token dan informasi pengguna
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userSummary));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestParam("name") String name,
                                          @RequestParam("username") String username,
                                          @RequestParam("email") String email,
                                          @RequestParam("password") String password,
                                          @RequestParam("photo") MultipartFile file,
                                          @RequestParam("role") String selectedRole) throws IOException {
        SignUpRequest signUpRequest = new SignUpRequest(name, username, email, password);
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }

        User result = authService.signUp(signUpRequest, file, selectedRole);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

}
