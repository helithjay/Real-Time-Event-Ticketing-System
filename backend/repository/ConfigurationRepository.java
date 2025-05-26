package com.Mycompany.TicketManagement.repository;

import com.Mycompany.TicketManagement.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

    Optional<Configuration> findByIsActiveTrue();

    @Query("SELECT c FROM Configuration c WHERE c.configName = ?1")
    Optional<Configuration> findByConfigName(String configName);

    boolean existsByConfigName(String configName);
}

