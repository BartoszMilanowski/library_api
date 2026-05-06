package com.bartmilan.library_api.service;

import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.model.User;
import com.bartmilan.library_api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
