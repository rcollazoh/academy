package cu.academy.student.course;

import cu.academy.shared.enum_types.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaSpecificationExecutor<StudentCourseEntity>, JpaRepository<StudentCourseEntity, Long> {
    @Query("SELECT sc FROM StudentCourseEntity sc WHERE sc.personId = :personId AND sc.status IN (:statuses)")
    List<StudentCourseEntity> findByPersonIdAndStatusIn(@Param("personId") long personId, @Param("statuses") List<CourseStatus> statuses);

    List<StudentCourseEntity> findByPersonId(long personId);
}
