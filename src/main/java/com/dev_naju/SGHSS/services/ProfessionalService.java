package com.dev_naju.SGHSS.services;

import com.dev_naju.SGHSS.dto.ProfessionalPatchRequestDTO;
import com.dev_naju.SGHSS.dto.ProfessionalRequestDTO;
import com.dev_naju.SGHSS.entities.ProfessionalHealth;
import com.dev_naju.SGHSS.entities.UserSystem;
import com.dev_naju.SGHSS.enums.UserRole;
import com.dev_naju.SGHSS.repositories.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {

    @Autowired
    ProfessionalRepository professionalRepository;

    public List<ProfessionalHealth> findAllProfessionalsHealthAuthorized(UserSystem currentUser) {
        if (currentUser.getRole() != UserRole.ADMIN &&
                currentUser.getRole() != UserRole.PATIENT &&
                currentUser.getRole() != UserRole.PROFESSIONAL_HEALTH) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado. Você não tem permissão para listar profissionais.");
        }
        return professionalRepository.findAll();
    }


    public Optional<ProfessionalHealth> findProfessionalHealthByIdAuthorized(Long id, UserSystem currentUser) {
        Optional<ProfessionalHealth> professionalOptional = professionalRepository.findById(id);

        if (professionalOptional.isEmpty()) {
            return Optional.empty();
        }

        ProfessionalHealth professional = professionalOptional.get();

        if (currentUser.getRole() == UserRole.ADMIN) {
            return Optional.of(professional);
        } else if (currentUser.getRole() == UserRole.PROFESSIONAL_HEALTH) {
            if (professional.getId().equals(currentUser.getId())) {
                return Optional.of(professional);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Você não tem permissão para visualizar este profissional.");
            }
        } else if (currentUser.getRole() == UserRole.PATIENT) {
            return Optional.of(professional);
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso não autorizado para visualizar este profissional.");
    }

//    @Transactional
//    public ProfessionalHealth createProfessionalHealthAuthorized(ProfessionalHealth professionalHealth, UserSystem currentUser) {
//        if (currentUser.getRole() != UserRole.ADMIN) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Apenas administradores podem criar novos profissionais de saúde.");
//        }
//        if (professionalRepository.findByLogin(professionalHealth.getLogin()).isPresent()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail de login já cadastrado para outro profissional de saúde.");
//        }
//        return professionalRepository.save(professionalHealth);
//    }


    @Transactional
    public ProfessionalHealth patchProfessionalHealthAuthorized(Long id, ProfessionalPatchRequestDTO dto, UserSystem currentUser) {
        ProfessionalHealth existingProfessional = findProfessionalHealthByIdAuthorized(id, currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional de saúde não encontrado."));

        if (currentUser.getRole() == UserRole.ADMIN) {
        } else if (currentUser.getRole() == UserRole.PROFESSIONAL_HEALTH) {
            if (!existingProfessional.getId().equals(currentUser.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Você só pode atualizar seus próprios dados de profissional.");
            }
            if (dto.email().isPresent() && !existingProfessional.getLogin().equals(dto.email().get())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para alterar seu e-mail de login diretamente.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Você não tem permissão para atualizar dados de profissional de saúde.");
        }

        dto.name().ifPresent(existingProfessional::setName);
        dto.email().ifPresent(email -> {
            if (!existingProfessional.getLogin().equals(email)) {
                Optional<ProfessionalHealth> professionalByEmail = professionalRepository.findByLogin(email);
                if (professionalByEmail.isPresent() && !professionalByEmail.get().getId().equals(id)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe outro profissional de saúde cadastrado com este e-mail.");
                }
                existingProfessional.setLogin(email);
            }
        });
        dto.specialty().ifPresent(existingProfessional::setProfessionalRole);
        dto.crm().ifPresent(existingProfessional::setCrm);

        return professionalRepository.save(existingProfessional);
    }

    @Transactional
    public void deleteProfessionalHealthAuthorized(Long id, UserSystem currentUser) {
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado: Apenas administradores podem deletar profissionais de saúde.");
        }
        if (!professionalRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional de saúde não encontrado com o ID: " + id);
        }

        professionalRepository.deleteById(id);
    }

}
