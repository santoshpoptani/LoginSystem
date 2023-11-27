package com.example.loginwithjwt.controller;

import com.example.loginwithjwt.jwt.Jwtutill;
import com.example.loginwithjwt.models.ERole;
import com.example.loginwithjwt.models.Role;
import com.example.loginwithjwt.models.UserEntity;
import com.example.loginwithjwt.payload.request.LoginRequest;
import com.example.loginwithjwt.payload.request.SignupRequest;
import com.example.loginwithjwt.payload.response.MessageResponse;
import com.example.loginwithjwt.payload.response.jwtResponse;
import com.example.loginwithjwt.repositories.RoleRepository;
import com.example.loginwithjwt.repositories.UserRepository;
import com.example.loginwithjwt.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin("*G")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager manager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private Jwtutill jwtutill;

    @Autowired
    public AuthController(AuthenticationManager manager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, Jwtutill jwtutill) {
        this.manager = manager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtutill = jwtutill;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),
                        request.getPassword()));

        String jwt = jwtutill.generateToken(request.getUsername());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(role-> role.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new jwtResponse(
                jwt,
                userDetails.getUsername(),
                roles,
                "Token Generated successfully"
        ));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: UserName Already Exsit"));
        }

        UserEntity user = new UserEntity(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (request.getRole() == null || request.getRole().isEmpty()) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(()->new RuntimeException("Role not Found"));
                roles.add(userRole);
        }else {
            request.getRole().forEach(role->{
                switch (role){
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(()->new RuntimeException("Role Not Found"));
                        roles.add(adminRole);
                        break;
                    case  "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                        .orElseThrow(()->new RuntimeException("Role not Found"));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                        .orElseThrow(()->new RuntimeException("Role not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Resisterd Successfully"));


    }



}
