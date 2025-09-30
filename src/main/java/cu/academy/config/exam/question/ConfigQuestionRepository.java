package cu.academy.config.exam.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ConfigQuestionRepository extends JpaSpecificationExecutor<ConfigQuestionEntity>, JpaRepository<ConfigQuestionEntity, Long> {
//    @Query("SELECT ce FROM ConfigExamEntity ce WHERE ce.module.id = :configModuleId")
//        Optional<ConfigExamEntity> findByConfigModuleId(long configModuleId);


    @Query("SELECT q FROM ConfigQuestionEntity q JOIN FETCH q.configOptions WHERE q.exam.id = :examId ORDER BY FUNCTION('RAND')")
    List<ConfigQuestionEntity> findRandomQuestions(@Param("examId") long examId, Pageable pageable);

}

