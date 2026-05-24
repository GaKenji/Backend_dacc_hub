package com.dacchub.backend.institution.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dacchub.backend.institution.entity.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, UUID> {

}
