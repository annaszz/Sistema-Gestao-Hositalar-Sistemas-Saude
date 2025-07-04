package com.dev_naju.SGHSS.services;

import com.dev_naju.SGHSS.dto.ConsultationRequestDTO;
import com.dev_naju.SGHSS.entities.Consultation;
import com.dev_naju.SGHSS.entities.Patient;
import com.dev_naju.SGHSS.entities.ProfessionalHealth;
import com.dev_naju.SGHSS.entities.UserSystem;
import com.dev_naju.SGHSS.enums.ProfessionalRole;
import com.dev_naju.SGHSS.enums.UserRole;
import com.dev_naju.SGHSS.repositories.ConsultationRepository;
import com.dev_naju.SGHSS.repositories.PatientRepository;
import com.dev_naju.SGHSS.repositories.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ProfessionalRepository professionalRepository;

    public List<Consultation> findAll() {
        return consultationRepository.findAll();
    }

    public List<Consultation> findByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado com o id: " + patientId));
        return consultationRepository.findByPatient(patient);
    }

    public List<Consultation> findByProfessionalId(Long professionalId) {
        ProfessionalHealth professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional de saúde não encontrado com o ID: " + professionalId));
        return consultationRepository.findByProfessional(professional);
    }


    public Optional<Consultation> findByIdAuthorized(Long id, UserSystem currentUser) {
        Optional<Consultation> consultationOptional = consultationRepository.findById(id);

        if (consultationOptional.isEmpty()) {
            return Optional.empty();
        }

        Consultation consultation = consultationOptional.get();

        if (currentUser.getRole() == UserRole.ADMIN) {
            return Optional.of(consultation);
        } else if (currentUser.getRole() == UserRole.PATIENT) {
            if (consultation.getPatient() != null && consultation.getPatient().getId().equals(currentUser.getId())) {
                return Optional.of(consultation);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Você não tem permissão para visualizar esta consulta.");
            }
        } else if (currentUser.getRole() == UserRole.PROFESSIONAL_HEALTH) {
            if (consultation.getProfessional() != null && consultation.getProfessional().getId().equals(currentUser.getId())) {
                return Optional.of(consultation);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Você não está envolvido nesta consulta.");
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso não autorizado para esta consulta.");
    }


    public Consultation schedule(ConsultationRequestDTO data) {
        Long actualPatientId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSystem currentUser = (UserSystem) authentication.getPrincipal();


        if (currentUser.getRole() == UserRole.PATIENT) {
            actualPatientId = currentUser.getId();
        } else if (currentUser.getRole() == UserRole.ADMIN || currentUser.getRole() == UserRole.PROFESSIONAL_HEALTH) {
            if (data.patientId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O 'patientId' é obrigatório para usuários Admin ou Profissional de Saúde ao agendar uma consulta.");
            }
            actualPatientId = data.patientId();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado para agendar consultas.");
        }


        Patient patient = patientRepository.findById(actualPatientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado com o ID: " +actualPatientId));

        ProfessionalHealth professional = null;

        List<ProfessionalHealth> availableDoctors = professionalRepository.findByProfessionalRole(ProfessionalRole.DOCTOR);

        if (availableDoctors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum profissional com a role " + data.professionalRole() + " disponível para atribuição automática.");
        }

        professional = availableDoctors.get(new Random().nextInt(availableDoctors.size()));

        Consultation newConsultation = new Consultation();
        newConsultation.setTypeConsultation("Online");
        newConsultation.setDate(data.date());
        newConsultation.setPatient(patient);
        newConsultation.setProfessional(professional);

        return consultationRepository.save(newConsultation);
    }

    @Transactional
    public void delete(Long consultationId) {
        if (!consultationRepository.existsById(consultationId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada para exclusão.");
        }
        consultationRepository.deleteById(consultationId);
    }

}