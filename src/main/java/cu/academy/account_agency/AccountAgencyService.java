package cu.academy.account_agency;

import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.utils.TranslatorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountAgencyService {
    private final AccountAgencyRepository repository;

//    private final ModelMapper modelMapper;
//    private static final Type listType = new TypeToken<List<NomAplicacionRespRedDto>>() {
//    }.getType();

    @Autowired
    public AccountAgencyService(AccountAgencyRepository repository) {
        this.repository = repository;
    }

    public AccountAgencyEntity getById(Long id) throws ArgumentException {
        return (repository.findById(id))
                .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION)));
    }

    public List<AccountAgencyEntity> getAllSort() {
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
    public AccountAgencyEntity insertConfig(AccountAgencyEntity entity) {
        return repository.save(entity);
    }

    @Transactional
    public AccountAgencyEntity updateConfig(Long id, AccountAgencyEntity entity) throws ArgumentException {
        if (!repository.existsById(id)) {
            throw new ArgumentException(Translator.toLocale(TranslatorCode.NO_TIPO_APLICACION));
        }
        return repository.save(entity);
    }
}

