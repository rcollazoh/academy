package cu.academy.shared.utils.customRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CustomRepository<T, ID extends Serializable>
        extends CrudRepository<T, ID> {
    void refresh(T t);
}
