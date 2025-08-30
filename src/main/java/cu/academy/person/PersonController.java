package cu.academy.person;


import cu.academy.person.dto.PersonProfileDto;
import cu.academy.person.dto.PersonRegisterDTO;
import cu.academy.person.dto.PersonUpdateDto;
import cu.academy.person.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/academy/person")
public class PersonController {

    private final PersonService service;
    private final PersonMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public PersonController(PersonService service, PersonMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<PersonProfileDto> getAllProfiles() {
        return service.getAllSort()
                      .stream()
                      .map(mapper::toProfileDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonProfileDto> getById(@PathVariable Long id) {
        PersonEntity person = service.getById(id);
        return person != null ? ResponseEntity.ok(mapper.toProfileDto(person)) : ResponseEntity.notFound().build();
    }

//    @PostMapping
//    public PersonProfileDto create(@RequestBody PersonInsertDto dto) {
//        PersonEntity saved = service.insertPerson(dto);
//        return mapper.toProfileDto(saved);
//    }

    @PostMapping(path = "register")
    public PersonProfileDto register(@RequestBody PersonRegisterDTO dto) {
        PersonEntity saved = service.registerPerson(dto);
        return mapper.toProfileDto(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonProfileDto> update(@PathVariable Long id, @RequestBody PersonUpdateDto dto) {
        PersonEntity entity = service.getById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        mapper.updateEntity(dto,entity);

        if (dto.password() != null)
            entity.setPassword( passwordEncoder.encode(dto.password()));

        return ResponseEntity.ok(mapper.toProfileDto(service.updatePerson(id,entity)));
    }
}
