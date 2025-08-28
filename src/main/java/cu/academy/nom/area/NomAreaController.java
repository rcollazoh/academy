package cu.academy.nom.area;


import cu.academy.nom.area.dto.NomAreaDto;
import cu.academy.nom.area.mapper.NomAreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/academy/area")
public class NomAreaController {

    private final NomAreaService service;
    private final NomAreaMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public NomAreaController(NomAreaService service, NomAreaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<NomAreaDto> getAll() {
        return service.getAllSort()
                      .stream()
                      .map(mapper::toDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NomAreaDto> getById(@PathVariable Long id) {
        NomAreaEntity person = service.getById(id);
        return person != null ? ResponseEntity.ok(mapper.toDto(person)) : ResponseEntity.notFound().build();
    }
}