package cu.academy.config.module;

import cu.academy.config.course.ConfigCourseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigModuleRepository extends JpaSpecificationExecutor<ConfigModuleEntity>, JpaRepository<ConfigModuleEntity, Long> {
    @Query("SELECT c FROM ConfigModuleEntity c WHERE c.course.id = :courseId")
    List<ConfigModuleEntity> findModulesByCourse(@Param("courseId") long courseId);

}
