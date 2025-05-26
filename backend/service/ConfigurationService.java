package com.Mycompany.TicketManagement.service;

import com.Mycompany.TicketManagement.dto.ConfigurationDto;
import com.Mycompany.TicketManagement.entity.Configuration;
import com.Mycompany.TicketManagement.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationService(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Transactional
    public ConfigurationDto saveConfiguration(ConfigurationDto configDto) {
        // Deactivate all existing configurations
        if (configDto.isActive()) {
            deactivateAllConfigurations();
        }

        Configuration config = convertToEntity(configDto);
        Configuration savedConfig = configurationRepository.save(config);
        System.out.println("Configuration saved with ID: " + savedConfig.getId());

        return convertToDto(savedConfig);
    }

    public List<ConfigurationDto> getAllConfigurations() {
        return configurationRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(java.util.stream.Collectors.toList());
    }

    public Optional<ConfigurationDto> getConfigurationById(Long id) {
        return configurationRepository.findById(id)
                .map(this::convertToDto);
    }

    public Optional<ConfigurationDto> getActiveConfiguration() {
        return configurationRepository.findByIsActiveTrue()
                .map(this::convertToDto);
    }

    @Transactional
    public ConfigurationDto activateConfiguration(Long id) {
        // Deactivate all configurations first
        deactivateAllConfigurations();

        Configuration config = configurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuration not found with ID: " + id));

        config.setActive(true);
        Configuration savedConfig = configurationRepository.save(config);

        System.out.println("Configuration activated with ID: " + savedConfig.getId());
        return convertToDto(savedConfig);
    }

    @Transactional
    public void deleteConfiguration(Long id) {
        configurationRepository.deleteById(id);
        System.out.println("Configuration deleted with ID: " + id);
    }

    private void deactivateAllConfigurations() {
        List<Configuration> activeConfigs = configurationRepository.findAll()
                .stream()
                .filter(Configuration::isActive)
                .collect(java.util.stream.Collectors.toList());

        activeConfigs.forEach(config -> config.setActive(false));
        configurationRepository.saveAll(activeConfigs);
    }

    private Configuration convertToEntity(ConfigurationDto dto) {
        Configuration config = new Configuration();
        config.setId(dto.getId());
        config.setTotalTickets(dto.getTotalTickets());
        config.setTicketReleaseRateMs(dto.getTicketReleaseRateMs());
        config.setCustomerRetrievalRateMs(dto.getCustomerRetrievalRateMs());
        config.setMaxTicketCapacity(dto.getMaxTicketCapacity());
        config.setConfigName(dto.getConfigName());
        config.setActive(dto.isActive());
        return config;
    }

    private ConfigurationDto convertToDto(Configuration entity) {
        ConfigurationDto dto = new ConfigurationDto();
        dto.setId(entity.getId());
        dto.setTotalTickets(entity.getTotalTickets());
        dto.setTicketReleaseRateMs(entity.getTicketReleaseRateMs());
        dto.setCustomerRetrievalRateMs(entity.getCustomerRetrievalRateMs());
        dto.setMaxTicketCapacity(entity.getMaxTicketCapacity());
        dto.setConfigName(entity.getConfigName());
        dto.setActive(entity.isActive());
        return dto;
    }
}
