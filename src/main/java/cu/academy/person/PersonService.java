package cu.academy.person;

import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.enum_types.EnumTipoPersona;
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
    private final PersonRepository personRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public PersonService(PersonRepository repository, PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    public PersonEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION)));
    }

    public List<PersonEntity> getAllSort() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Transactional
    public void deleteConfig(Long id) throws ArgumentException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION));
        }
    }

    @Transactional
    public PersonEntity insertPerson(PersonEntity entity) {
        checkEmailNotExist(entity);

        if (entity.getPassword() != null)
            entity.setPassword( passwordEncoder.encode(entity.getPassword()));

        return repository.save(entity);
    }

    @Transactional
    public PersonEntity updatePerson(Long id, PersonEntity entity) throws ArgumentException {
        if (!repository.existsById(id)) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION));
        }

        return repository.save(entity);
    }

    public void validatePersonTypeForPerson(PersonEntity person, EnumTipoPersona tipoPersona) throws ArgumentException {
        if (person != null && tipoPersona != null)
            if ((!person.getIsClient() && tipoPersona.equals(EnumTipoPersona.CLIENT)))
                throw new ArgumentException(Translator.toLocale(TranslatorCode.INVALID_PERSON_TYPE));
    }

    private void checkEmailNotExist(PersonEntity personaEntity) throws ArgumentException {
        Optional<PersonEntity> persona = personRepository.findByEmail(personaEntity.getEmail());
        if (persona.isPresent()) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.PERSON_EXISTE_NUM_CEL));
        }

    }

}

