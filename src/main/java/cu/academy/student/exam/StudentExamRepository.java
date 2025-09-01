package cu.academy.student.exam;

import cu.academy.config.exam.ConfigExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentExamRepository extends JpaSpecificationExecutor<StudentExamEntity>, JpaRepository<StudentExamEntity, Long> {
    @Query("SELECT ce FROM StudentExamEntity ce WHERE ce.studentModule.id = :configModuleId")
        Optional<StudentExamEntity> findByConfigModuleId(long configModuleId);
}

