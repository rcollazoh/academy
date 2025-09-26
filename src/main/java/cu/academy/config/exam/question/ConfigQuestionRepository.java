package cu.academy.config.exam.question;


import cu.academy.config.classes.image.dto.ClassImageNavigationView;
import cu.academy.config.exam.question.dto.ExamQuestionNavigationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigQuestionRepository extends JpaSpecificationExecutor<ConfigQuestionEntity>, JpaRepository<ConfigQuestionEntity, Long> {
//    @Query("SELECT ce FROM ConfigExamEntity ce WHERE ce.module.id = :configModuleId")
//        Optional<ConfigExamEntity> findByConfigModuleId(long configModuleId);

    @Query(value = """
        SELECT 
            current.id AS currentId,
            current.text AS title,
            current.order_num AS orderNum,
            0 AS previousId,
            next.id AS nextId
        FROM config_question current
        LEFT JOIN config_question next
            ON next.exam_id = current.exam_id AND next.order_num = current.order_num + 1
        WHERE current.exam_id = :examId and current.order_num = 1
        """, nativeQuery = true)
    ExamQuestionNavigationView findNavigationByIdExam(@Param("examId") Long examId);

}

