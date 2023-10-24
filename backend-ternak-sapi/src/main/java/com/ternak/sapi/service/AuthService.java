package com.ternak.sapi.service;

import com.ternak.sapi.exception.AppException;
import com.ternak.sapi.model.Role;
import com.ternak.sapi.model.enumeration.RoleName;
import com.ternak.sapi.model.User;
import com.ternak.sapi.payload.SignUpRequest;
import com.ternak.sapi.repository.RoleRepository;
import com.ternak.sapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;
@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final String FOLDER_PATH="E:/Myfile/Magang Maching Fund/img"; 

    public User signUp(SignUpRequest signUpRequest, MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        // Generate a random name for the image
        String randomName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(originalFilename);
        String filePath = FOLDER_PATH+randomName;
        File destFile = new File(FOLDER_PATH, randomName);
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), filePath);
        file.transferTo(destFile);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_ADMINISTRATOR)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }
}
