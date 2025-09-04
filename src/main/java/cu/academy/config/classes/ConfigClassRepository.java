package cu.academy.config.classes;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigClassRepository extends JpaSpecificationExecutor<ConfigClassEntity>, JpaRepository<ConfigClassEntity, Long> {
    @Query("SELECT c FROM ConfigClassEntity c WHERE c.module.id = :moduleId")
    List<ConfigClassEntity> findClassesByModule(@Param("moduleId") long moduleId);

}
