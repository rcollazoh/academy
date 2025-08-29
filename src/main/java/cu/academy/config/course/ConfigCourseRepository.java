package cu.academy.config.course;


import cu.academy.nom.area.NomAreaEntity;
import cu.academy.nom.practice.NomPracticeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigCourseRepository extends JpaSpecificationExecutor<ConfigCourseEntity>, JpaRepository<ConfigCourseEntity, Long> {
    List<ConfigCourseEntity> findByActiveTrue(Sort sort);
    @Query("SELECT c FROM ConfigCourseEntity c WHERE c.area.id = :areaId AND c.practice.id = :practiceId")
    ConfigCourseEntity findByAreaIdAndPracticeId(@Param("areaId") long areaId, @Param("practiceId") long practiceId);

}
