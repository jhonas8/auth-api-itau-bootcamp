package web.authapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import web.authapi.models.ERole;
import web.authapi.models.Role;
import web.authapi.models.User;
import web.authapi.payload.request.AuthRequest;
import web.authapi.payload.request.LoginRequest;
import web.authapi.payload.request.SignupRequest;
import web.authapi.payload.response.AuthResponse;
import web.authapi.payload.response.MessageResponse;
import web.authapi.payload.response.UserResponse;
import web.authapi.repository.RoleRepository;
import web.authapi.repository.UserRepository;
import web.authapi.security.jwt.JwtUtils;
import web.authapi.security.services.UserDetailsImpl;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins="*",maxAge = 3600)
@RestController
@RequestMapping("/api/itau/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authManager;
    @Autowired
    UserRepository repository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/validate")
    public ResponseEntity<?> validateRequest(
            @Valid
            @RequestBody
            AuthRequest
            authRequest
    ) {
        boolean isValid = jwtUtils
                .validateJwtToken(authRequest.getToken());

        String username = jwtUtils
                .getUsernameFromJwtToken(authRequest.getToken());

        User user = repository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("Not found user!"));

        List<String> roles = user.getRoles().stream()
                .map(role->role.getName().name())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(
                        new AuthResponse(
                                isValid,
                                user.getId(),
                                roles
                        )
                );
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(
            @Valid
            @RequestBody
            LoginRequest
            loginRequest
    ) {
        Authentication auth = authManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        String returningToken = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserResponse(
                        userDetails.getId(),
                        roles,
                        returningToken
                ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(
            @Valid
            @RequestBody
            SignupRequest
            signupRequest
    ) {
        if(repository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(
                            new MessageResponse("Error: Username already exists!")
                    );
        }

        if(repository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new MessageResponse("Error: Email already exists!")
                    );
        }

        User user = new User(
                signupRequest.getEmail(),
                signupRequest.getUsername(),
                encoder.encode(signupRequest.getPassword())
        );

        Set<Role> roles = new HashSet<>();

        Role leitorRole = new Role(ERole.LEITOR);

        roles.add(leitorRole);
        user.setRoles(roles);

        roleRepository.save(leitorRole);
        repository.save(user);

        return ResponseEntity
                .ok(new MessageResponse("User was registered!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("User was signed out!"));
    }
}
