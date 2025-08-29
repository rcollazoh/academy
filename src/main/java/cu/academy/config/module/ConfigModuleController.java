package cu.academy.config.module;


import cu.academy.config.module.dto.ConfigModuleDto;
import cu.academy.config.module.mapper.ConfigModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/academy/module")
public class ConfigModuleController {

    private final ConfigModuleService service;
    private final ConfigModuleMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ConfigModuleController(ConfigModuleService service, ConfigModuleMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ConfigModuleDto> getAll() {
        return service.getAllSort()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigModuleDto> getById(@PathVariable Long id) {
        ConfigModuleEntity person = service.getById(id);
        return person != null ? ResponseEntity.ok(mapper.toDto(person)) : ResponseEntity.notFound().build();
    }

    @GetMapping("/by-course/{courseId}")
    public List<ConfigModuleDto> getModuleByCourseId(@PathVariable Long courseId) {
        List<ConfigModuleEntity> courseResponse = service.getModuleByCourse(courseId);
        return courseResponse.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}