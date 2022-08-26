package com.rh.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rh.management.model.Compensation;

public interface CompensationRepository extends JpaRepository<Compensation, Long> {

}
