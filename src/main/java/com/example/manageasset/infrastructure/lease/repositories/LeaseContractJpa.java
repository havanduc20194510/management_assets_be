package com.example.manageasset.infrastructure.lease.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaseContractJpa extends JpaRepository<LeaseContractEntity, String> {
}
