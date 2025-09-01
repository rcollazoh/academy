package cu.academy.config.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigExamRepository extends JpaSpecificationExecutor<ConfigExamEntity>, JpaRepository<ConfigExamEntity, Long> {
    @Query("SELECT ce FROM ConfigExamEntity ce WHERE ce.module.id = :configModuleId")
        Optional<ConfigExamEntity> findByConfigModuleId(long configModuleId);
}

