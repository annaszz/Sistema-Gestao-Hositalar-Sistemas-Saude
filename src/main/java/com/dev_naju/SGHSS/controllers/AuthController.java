package com.dev_naju.SGHSS.controllers;

import com.dev_naju.SGHSS.dto.AuthRequest;
import com.dev_naju.SGHSS.dto.LoginResponseDTO;
import com.dev_naju.SGHSS.dto.RegisterDTO;
import com.dev_naju.SGHSS.entities.Admin;
import com.dev_naju.SGHSS.entities.Patient;
import com.dev_naju.SGHSS.entities.ProfessionalHealth;
import com.dev_naju.SGHSS.entities.UserSystem;
import com.dev_naju.SGHSS.enums.UserRole;
import com.dev_naju.SGHSS.repositories.UserRepository;
import com.dev_naju.SGHSS.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private AuthenticationManager manager;

    @Autowired private UserRepository repository;

    @Autowired TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthRequest dto){
        var userNamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = this.manager.authenticate(userNamePassword);

        UserSystem user = (UserSystem) auth.getPrincipal();
        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO dto){
        Optional<UserSystem> existingUser = this.repository.findByLogin(dto.login());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Login/Email já cadastrado.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        UserSystem newUser;

        if(dto.role() == UserRole.PATIENT){
            if(dto.gender() == null || dto.dateBirth() == null || dto.address() == null){
                return ResponseEntity.badRequest().body("Dados de gênero, data de nascimento e endereço são obrigatórios para Pacientes.");
            }
            newUser = new Patient(dto.login(),encryptedPassword, dto.name(), dto.gender(), dto.dateBirth(), dto.address().toEntity());
            repository.save(newUser);
        } else if (dto.role() == UserRole.PROFESSIONAL_HEALTH) {
            newUser = new ProfessionalHealth(dto.login(), encryptedPassword, dto.name(), dto.crm(), dto.professionalRole());
            repository.save(newUser);
        } else if (dto.role() == UserRole.ADMIN) {
            newUser = new Admin(dto.login(), encryptedPassword, dto.name());
            repository.save(newUser);
        } else {
            return ResponseEntity.badRequest().body("ROLE DESCONHECIDA " + dto.role());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso!");
    }


}
