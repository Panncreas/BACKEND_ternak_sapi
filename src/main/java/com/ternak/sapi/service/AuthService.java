package com.ternak.sapi.service;

import com.ternak.sapi.exception.AppException;
import com.ternak.sapi.model.Role;
import com.ternak.sapi.model.enumeration.RoleName;
import com.ternak.sapi.model.User;
import com.ternak.sapi.payload.SignUpRequest;
import com.ternak.sapi.repository.RoleRepository;
import com.ternak.sapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthService(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadDir = uploadDir;
        init();
    }

    public void init() {
    try {
        Path path = Paths.get(System.getProperty("user.dir"), "uploads");
        Files.createDirectories(path);
    } catch (IOException e) {
        throw new RuntimeException("Failed to create upload directory", e);
    }
}


    public User signUp(SignUpRequest signUpRequest, MultipartFile file, String selectedRole) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String randomName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(originalFilename);
        String filePath = uploadDir + File.separator + randomName;
        File destFile = new File(uploadDir, randomName);

        // Check if the selected role is valid
        Role userRole = roleRepository.findByName(RoleName.valueOf(selectedRole))
                .orElseThrow(() -> new AppException("Invalid User Role."));

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), filePath);
        file.transferTo(destFile);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set the user's roles
        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }
}
