package cu.academy.nom.practice;


import cu.academy.nom.area.NomAreaEntity;
import cu.academy.nom.area.NomAreaService;
import cu.academy.nom.area.dto.NomAreaDto;
import cu.academy.nom.area.mapper.NomAreaMapper;
import cu.academy.nom.practice.dto.NomPracticeDto;
import cu.academy.nom.practice.mapper.NomPracticeMapper;
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
@RequestMapping("/academy/practice")
public class NomPracticeController {

    private final NomPracticeService service;
    private final NomPracticeMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public NomPracticeController(NomPracticeService service, NomPracticeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<NomPracticeDto> getAll() {
        return service.getAllSort()
                      .stream()
                      .map(mapper::toDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/area/{areaId}")
    public List<NomPracticeDto> getAllByArea(@PathVariable Long areaId) {
        return service.getAllSortByArea(areaId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NomPracticeDto> getById(@PathVariable Long id) {
        NomPracticeEntity person = service.getById(id);
        return person != null ? ResponseEntity.ok(mapper.toDto(person)) : ResponseEntity.notFound().build();
    }
}