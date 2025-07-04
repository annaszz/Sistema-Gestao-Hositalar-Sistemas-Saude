package com.dev_naju.SGHSS.controllers;

import com.dev_naju.SGHSS.dto.ProfessionalPatchRequestDTO;
import com.dev_naju.SGHSS.dto.ProfessionalResponseDTO;
import com.dev_naju.SGHSS.entities.ProfessionalHealth;
import com.dev_naju.SGHSS.entities.UserSystem;
import com.dev_naju.SGHSS.enums.UserRole;
import com.dev_naju.SGHSS.services.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/professionals")
public class ProfessionalController {
    @Autowired
    ProfessionalService professionalHealthService;


    @GetMapping
    public ResponseEntity<List<ProfessionalResponseDTO>> findAllProfessionals() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();

        List<ProfessionalHealth> professionals = professionalHealthService.findAllProfessionalsHealthAuthorized(currentUser);
        List<ProfessionalResponseDTO> dtos = professionals.stream()
                .map(ProfessionalResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalResponseDTO> findProfessionalById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();

        ProfessionalHealth professional = professionalHealthService.findProfessionalHealthByIdAuthorized(id, currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional de saúde não encontrado."));

        return ResponseEntity.ok(new ProfessionalResponseDTO(professional));
    }


    @GetMapping("/me")
    public ResponseEntity<ProfessionalResponseDTO> getMyProfessionalData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();

        if (currentUser.getRole() != UserRole.PROFESSIONAL_HEALTH) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Este endpoint é apenas para profissionais de saúde.");
        }

        ProfessionalHealth professional = professionalHealthService.findProfessionalHealthByIdAuthorized(currentUser.getId(), currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dados de profissional de saúde não encontrados para o usuário logado."));

        return ResponseEntity.ok(new ProfessionalResponseDTO(professional));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProfessionalResponseDTO> patchProfessional(@PathVariable Long id, @RequestBody ProfessionalPatchRequestDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();

        ProfessionalHealth updatedProfessional = professionalHealthService.patchProfessionalHealthAuthorized(id, dto, currentUser);

        return ResponseEntity.ok(new ProfessionalResponseDTO(updatedProfessional));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessional(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();

        professionalHealthService.deleteProfessionalHealthAuthorized(id, currentUser);
        return ResponseEntity.noContent().build();
    }

}
