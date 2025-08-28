package cu.academy.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaSpecificationExecutor<PersonEntity>, JpaRepository<PersonEntity, Long> {
    Optional<PersonEntity> findByEmail(String email);
}
