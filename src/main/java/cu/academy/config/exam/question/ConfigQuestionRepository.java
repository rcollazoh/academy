package cu.academy.config.exam.question;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigQuestionRepository extends JpaSpecificationExecutor<ConfigQuestionEntity>, JpaRepository<ConfigQuestionEntity, Long> {
//    @Query("SELECT ce FROM ConfigExamEntity ce WHERE ce.module.id = :configModuleId")
//        Optional<ConfigExamEntity> findByConfigModuleId(long configModuleId);
}

