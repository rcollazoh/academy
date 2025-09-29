package cu.academy.person;

import cu.academy.email.EmailService;
import cu.academy.nom.area.NomAreaEntity;
import cu.academy.nom.area.NomAreaRepository;
import cu.academy.nom.practice.NomPracticeEntity;
import cu.academy.nom.practice.NomPracticeRepository;
import cu.academy.person.dto.PersonRegisterDTO;
import cu.academy.person.mapper.PersonMapper;
import cu.academy.role.RoleRepository;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.enum_types.EnumRole;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository repository;
    private final NomAreaRepository nomAreaRepository;
    private final NomPracticeRepository nomPracticeRepository;
    private final RoleRepository roleRepository;

    private final EmailService  emailService;
    private final PersonMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public PersonService(PersonRepository repository, EmailService emailService, NomAreaRepository nomAreaRepository, NomPracticeRepository nomPracticeRepository, RoleRepository roleRepository, PersonMapper mapper) {
        this.repository = repository;
        this.emailService = emailService;
        this.nomAreaRepository = nomAreaRepository;
        this.nomPracticeRepository = nomPracticeRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    public PersonEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT)));
    }

    public List<PersonEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Transactional
    public void deleteConfig(Long id) throws ArgumentException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT));
        }
    }

    @Transactional
    public PersonEntity registerPerson(PersonRegisterDTO dto) {

        checkEmailNotExist(dto.email());

        PersonEntity entity = mapper.toEntity(dto);

        NomAreaEntity area = nomAreaRepository.findById(dto.areaId())
                .orElseThrow(() -> new ArgumentException("Area not found"));

        NomPracticeEntity practice = nomPracticeRepository.findById(dto.practiceId())
                .orElseThrow(() -> new ArgumentException("Practice not found"));

        entity.setArea(area);
        entity.setPractice(practice);
        entity.setRole(roleRepository.findByName(EnumRole.STUDENT.name()).get());
        if (entity.getPassword() != null)
            entity.setPassword( passwordEncoder.encode(entity.getPassword()));

      //  emailService.sendMessage(entity.getEmail(), "Prod Acedemy", "We are testing new functionality", null);
        return repository.save(entity);
    }

    @Transactional
    public PersonEntity updatePerson(Long id, PersonEntity entity) throws ArgumentException {
        if (!repository.existsById(id)) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_EXISTE_ELEMENT));
        }

        return repository.save(entity);
    }

    private void checkEmailNotExist(String email) throws ArgumentException {
        Optional<PersonEntity> persona = repository.findByEmail(email);
        if (persona.isPresent()) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.PERSON_EXISTE_NUM_CEL));
        }

    }

}

