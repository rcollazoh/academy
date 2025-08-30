package cu.academy.config.parameter;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigParameterRepository extends JpaSpecificationExecutor<ConfigParameterEntity>, JpaRepository<ConfigParameterEntity, Long> {
    Optional<ConfigParameterEntity> findByName(String name);
}
