package com.dev_naju.SGHSS.controllers;

import com.dev_naju.SGHSS.dto.ConsultationRequestDTO;
import com.dev_naju.SGHSS.dto.ConsultationResponseDTO;
import com.dev_naju.SGHSS.entities.Consultation;
import com.dev_naju.SGHSS.entities.UserSystem;
import com.dev_naju.SGHSS.enums.UserRole;
import com.dev_naju.SGHSS.services.ConsultationService;
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
@RequestMapping("/api/consultations")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;


    @GetMapping
    public ResponseEntity<List<ConsultationResponseDTO>> getAllConsultations() {
        List<Consultation> consultations = consultationService.findAll();
        List<ConsultationResponseDTO> dtos = consultations.stream()
                .map(ConsultationResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/me")
    public ResponseEntity<List<ConsultationResponseDTO>> getMyConsultations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal(); // Obtém o usuário logado

        List<Consultation> myConsultations;

        if (currentUser.getRole() == UserRole.PATIENT) {
            myConsultations = consultationService.findByPatient(currentUser.getId());
        } else if (currentUser.getRole() == UserRole.PROFESSIONAL_HEALTH) {
            myConsultations = consultationService.findByProfessionalId(currentUser.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado. Este endpoint é para 'minhas' consultas (paciente ou profissional de saúde).");
        }

        List<ConsultationResponseDTO> dtos = myConsultations.stream()
                .map(ConsultationResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponseDTO> getConsultationById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();

        Consultation consultation = consultationService.findByIdAuthorized(id, currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada ou acesso negado."));

        return ResponseEntity.ok(new ConsultationResponseDTO(consultation));
    }

    @PostMapping("/schedule")
    public ResponseEntity<ConsultationResponseDTO> scheduleConsultation(@RequestBody ConsultationRequestDTO requestDTO){
        Consultation newConsultation = consultationService.schedule(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ConsultationResponseDTO(newConsultation));
    }

    @DeleteMapping("/{consultationId}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long consultationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();

        Consultation consultationToDelete = consultationService.findByIdAuthorized(consultationId, currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada."));

        if (currentUser.getRole() == UserRole.ADMIN) {
            consultationService.delete(consultationId);
            return ResponseEntity.noContent().build();
        } else if (currentUser.getRole() == UserRole.PATIENT) {
            consultationService.delete(consultationId);
            return ResponseEntity.noContent().build();
        } else if (currentUser.getRole() == UserRole.PROFESSIONAL_HEALTH) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Profissionais de saúde não têm permissão para apagar consultas.");
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso não autorizado para apagar esta consulta.");
        }
    }

}
