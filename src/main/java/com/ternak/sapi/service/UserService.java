/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ternak.sapi.service;

import com.ternak.sapi.exception.ResourceNotFoundException;
import com.ternak.sapi.model.Role;
import com.ternak.sapi.model.User;
import com.ternak.sapi.payload.UserResponse;
import com.ternak.sapi.repository.UserRepository;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public UserResponse getUserById(Long userIds) {
    User user = userRepository.findById(userIds).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", userIds));

    Role userRole = user.getRoles().iterator().next(); // Assuming there's only one role per user

    UserResponse userResponse = new UserResponse();
    userResponse.setId(user.getId());
    userResponse.setName(user.getName());
    userResponse.setEmail(user.getEmail());
    userResponse.setRole(userRole.getName().toString());
    userResponse.setAvatar(user.getPhoto());
    return userResponse;
}

}
