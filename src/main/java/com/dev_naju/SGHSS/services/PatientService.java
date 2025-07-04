package com.dev_naju.SGHSS.services;

import com.dev_naju.SGHSS.dto.PatientPatchRequestDTO;
import com.dev_naju.SGHSS.entities.Address;
import com.dev_naju.SGHSS.entities.Patient;
import com.dev_naju.SGHSS.entities.UserSystem;
import com.dev_naju.SGHSS.enums.UserRole;
import com.dev_naju.SGHSS.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.dev_naju.SGHSS.entities.Address;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired private PatientRepository patientRepository;

    public List<Patient> findAllPatientsAuthorized(UserSystem currentUser) {
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado. Apenas administradores podem listar todos os pacientes.");
        }
        return patientRepository.findAll();
    }

    public Optional<Patient> findPatientByIdAuthorized(Long id, UserSystem currentUser) {
        Optional<Patient> patientOptional = patientRepository.findById(id);

        if (patientOptional.isEmpty()) {
            return Optional.empty();
        }

        Patient patient = patientOptional.get();

        if (currentUser.getRole() == UserRole.ADMIN) {
            return Optional.of(patient);
        } else if (currentUser.getRole() == UserRole.PATIENT) {
            if (patient.getId().equals(currentUser.getId())) {
                return Optional.of(patient);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Você não tem permissão para visualizar este paciente.");
            }
        } else if (currentUser.getRole() == UserRole.PROFESSIONAL_HEALTH) {
            return Optional.of(patient);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso não autorizado para visualizar este paciente.");
    }

    @Transactional
    public Patient patchPatientAuthorized(Long id, PatientPatchRequestDTO dto, UserSystem currentUser) {
        Patient existingPatient = findPatientByIdAuthorized(id, currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado."));

        if (currentUser.getRole() == UserRole.ADMIN) {
        } else if (currentUser.getRole() == UserRole.PATIENT) {
            if (!existingPatient.getId().equals(currentUser.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Você só pode atualizar seus próprios dados de paciente.");
            }
            if (dto.email().isPresent() && !existingPatient.getLogin().equals(dto.email().get())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para alterar seu e-mail de login diretamente.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Você não tem permissão para atualizar dados de paciente.");
        }
        dto.name().ifPresent(existingPatient::setName);
        dto.email().ifPresent(email -> {
            if (!existingPatient.getLogin().equals(email)) {
                Optional<Patient> patientByEmail = patientRepository.findByLogin(email);
                if (patientByEmail.isPresent() && !patientByEmail.get().getId().equals(id)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe outro paciente cadastrado com este e-mail.");
                }
                existingPatient.setLogin(email);
            }
        });
        dto.gender().ifPresent(existingPatient::setGender);
        dto.dateBirth().ifPresent(existingPatient::setDateBirth);

        return patientRepository.save(existingPatient);
    }

    @Transactional
    public void deletePatientAuthorized(Long id, UserSystem currentUser) {
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Apenas administradores podem deletar pacientes.");
        }
        if (!patientRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado com o ID: " + id);
        }

        patientRepository.deleteById(id);
    }

}
