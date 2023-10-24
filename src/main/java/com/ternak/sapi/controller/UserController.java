package com.ternak.sapi.controller;

import com.ternak.sapi.payload.UserSummary;
import com.ternak.sapi.payload.UserProfile;
import com.ternak.sapi.payload.UserIdentityAvailability;
import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.User;
import com.ternak.sapi.payload.UserResponse;
import com.ternak.sapi.security.CurrentUser;
import com.ternak.sapi.security.UserPrincipal;
import com.ternak.sapi.service.HewanService;
import com.ternak.sapi.util.AppConstants;
import com.ternak.sapi.repository.UserRepository;
import com.ternak.sapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
@Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HewanService pollService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        String roles = currentUser.getAuthorities().toString();
        String role = roles.replaceAll("[\\[\\]]", "");
        UserResponse user = userService.getUserById(currentUser.getId());
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(), user.getEmail(), user.getRole(), user.getAvatar());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt());
        return userProfile;
    }

}
