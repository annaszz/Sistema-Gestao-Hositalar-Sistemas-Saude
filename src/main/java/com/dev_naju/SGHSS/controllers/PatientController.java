package com.dev_naju.SGHSS.controllers;

import com.dev_naju.SGHSS.dto.PatientPatchRequestDTO;
import com.dev_naju.SGHSS.dto.PatientResponseAllDTO;
import com.dev_naju.SGHSS.dto.PatientResponseDTO;
import com.dev_naju.SGHSS.entities.Patient;
import com.dev_naju.SGHSS.entities.UserSystem;
import com.dev_naju.SGHSS.enums.UserRole;
import com.dev_naju.SGHSS.services.PatientService;
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
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> findAllPatients() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();

        List<Patient> patients = patientService.findAllPatientsAuthorized(currentUser);
        List<PatientResponseDTO> dtos = patients.stream()
                .map(PatientResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseAllDTO> findPatientById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();

        Patient patient = patientService.findPatientByIdAuthorized(id, currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));

        return ResponseEntity.ok(new PatientResponseAllDTO(patient));
    }


    @GetMapping("/me")
    public ResponseEntity<PatientResponseDTO> getMyPatientData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();

        if (currentUser.getRole() != UserRole.PATIENT) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Este endpoint é apenas para pacientes.");
        }

        Patient patient = patientService.findPatientByIdAuthorized(currentUser.getId(), currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dados de paciente não encontrados para o usuário logado."));

        return ResponseEntity.ok(new PatientResponseDTO(patient));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> patchPatient(@PathVariable Long id, @RequestBody PatientPatchRequestDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();

        Patient updatedPatient = patientService.patchPatientAuthorized(id, dto, currentUser);

        return ResponseEntity.ok(new PatientResponseDTO(updatedPatient));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();

        patientService.deletePatientAuthorized(id, currentUser);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}



