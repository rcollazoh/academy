package cu.academy.nom.practice;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;

@Repository
public interface NomPracticeRepository extends JpaSpecificationExecutor<NomPracticeEntity>, JpaRepository<NomPracticeEntity, Long> {
    List<NomPracticeEntity> findByActiveTrue(Sort sort);
    List<NomPracticeEntity> findByAreaIdAndActiveTrueOrderByNameAsc(Long areaId);

}
