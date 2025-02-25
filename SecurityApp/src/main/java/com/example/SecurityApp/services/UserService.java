package com.example.SecurityApp.services;

import com.example.SecurityApp.dto.LoginDTO;
import com.example.SecurityApp.dto.SignUpDTO;
import com.example.SecurityApp.dto.UserDTO;
import com.example.SecurityApp.entities.User;
import com.example.SecurityApp.exceptions.ResourceNotFoundException;
import com.example.SecurityApp.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElseThrow(() -> new BadCredentialsException("User with email not"+username+" found"));
    }

    public User getUserById(Long userId){
        return userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with this " +userId+" not found"));
    }

    public UserDTO signUp(SignUpDTO sign) {
        Optional<User> user = userRepo.findByEmail(sign.getEmail());

        if(user.isPresent()){
            throw new BadCredentialsException("User with email already exists"+sign.getEmail());
        }

        User toCreate = modelMapper.map(sign,User.class);
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
        User savedUser = userRepo.save(toCreate);

        return modelMapper.map(savedUser,UserDTO.class);
    }


}
