package com.Mycompany.TicketManagement.controller;

import com.Mycompany.TicketManagement.dto.ConfigurationDto;
import com.Mycompany.TicketManagement.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/configurations")
@CrossOrigin(origins = "*")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @Autowired
    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping
    public ResponseEntity<ConfigurationDto> createConfiguration(@RequestBody ConfigurationDto configDto) {
        ConfigurationDto savedConfig = configurationService.saveConfiguration(configDto);
        return new ResponseEntity<>(savedConfig, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ConfigurationDto>> getAllConfigurations() {
        List<ConfigurationDto> configurations = configurationService.getAllConfigurations();
        return ResponseEntity.ok(configurations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationDto> getConfigurationById(@PathVariable Long id) {
        Optional<ConfigurationDto> config = configurationService.getConfigurationById(id);
        return config.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<ConfigurationDto> getActiveConfiguration() {
        Optional<ConfigurationDto> config = configurationService.getActiveConfiguration();
        return config.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<ConfigurationDto> activateConfiguration(@PathVariable Long id) {
        try {
            ConfigurationDto config = configurationService.activateConfiguration(id);
            return ResponseEntity.ok(config);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfiguration(@PathVariable Long id) {
        configurationService.deleteConfiguration(id);
        return ResponseEntity.noContent().build();
    }
}
