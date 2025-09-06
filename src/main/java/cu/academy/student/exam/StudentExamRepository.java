package cu.academy.student.exam;

import cu.academy.config.exam.ConfigExamEntity;
import cu.academy.shared.enum_types.EnumExamStatus;
import cu.academy.shared.enum_types.EnumModuleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StudentExamRepository extends JpaSpecificationExecutor<StudentExamEntity>, JpaRepository<StudentExamEntity, Long> {
    @Query("SELECT ce FROM StudentExamEntity ce WHERE ce.studentModule.id = :configModuleId")
        Optional<StudentExamEntity> findByConfigModuleId(long configModuleId);

    @Modifying
    @Transactional
    @Query("UPDATE StudentExamEntity s SET s.status = :status WHERE s.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") EnumExamStatus status);
}

