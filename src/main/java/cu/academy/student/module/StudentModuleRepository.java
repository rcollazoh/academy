package cu.academy.student.module;

import cu.academy.shared.enum_types.EnumModuleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudentModuleRepository extends JpaSpecificationExecutor<StudentModuleEntity>, JpaRepository<StudentModuleEntity, Long> {
    @Query("SELECT sm FROM StudentModuleEntity sm WHERE sm.studentCourse.id = :studentCourseId order by sm.module.orderNum")
    List<StudentModuleEntity> findFullModulesByStudentCourseId(@Param("studentCourseId") long studentCourseId);
    @Query("SELECT m.status FROM StudentModuleEntity m WHERE m.studentCourse.id = :courseId")
    List<EnumModuleStatus> findStatusesByCourseId(@Param("courseId") Long courseId);

    @Modifying
    @Transactional
    @Query("UPDATE StudentModuleEntity s SET s.status = :status WHERE s.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") EnumModuleStatus status);
}
