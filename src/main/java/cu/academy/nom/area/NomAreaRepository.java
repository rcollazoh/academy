package cu.academy.nom.area;


import cu.academy.nom.practice.NomPracticeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NomAreaRepository extends JpaSpecificationExecutor<NomAreaEntity>, JpaRepository<NomAreaEntity, Long> {
    List<NomAreaEntity> findByActiveTrue(Sort sort);

}
