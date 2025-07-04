package com.dev_naju.SGHSS.repositories;

import com.dev_naju.SGHSS.entities.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {
}
