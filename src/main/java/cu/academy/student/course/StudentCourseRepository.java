package cu.academy.student.course;

import cu.academy.shared.enum_types.EnumCourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaSpecificationExecutor<StudentCourseEntity>, JpaRepository<StudentCourseEntity, Long> {
    @Query("SELECT sc FROM StudentCourseEntity sc WHERE sc.personId = :personId AND sc.status IN (:statuses)")
    List<StudentCourseEntity> findByPersonIdAndStatusIn(@Param("personId") long personId, @Param("statuses") List<EnumCourseStatus> statuses);

    List<StudentCourseEntity> findByPersonId(long personId);

    @Modifying
    @Transactional
    @Query("UPDATE StudentCourseEntity s SET s.status = :status WHERE s.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") EnumCourseStatus status);

    @Query(value = """
    SELECT sc.*
    FROM student_course sc
    JOIN config_course cc ON sc.course_id = cc.id
    WHERE sc.status = 'ACTIVATED'
      AND sc.start_date IS NOT NULL
      AND cc.duration_days IS NOT NULL
      AND DATE_ADD(sc.start_date, INTERVAL cc.duration_days DAY) < CURDATE()
    """, nativeQuery = true)
    List<StudentCourseEntity> findExpiredActivatedCourses();

}
